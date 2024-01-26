package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.*;
import org.example.entity.BuilderObject;
import org.example.entity.ImagesForObject;
import org.example.entity.Layout;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.type.TypeObject;
import org.example.mapper.LayoutMapper;
import org.example.mapper.ObjectBuilderMapper;
import org.example.service.ImagesForObjectService;
import org.example.service.LayoutService;
import org.example.service.MinioService;
import org.example.service.ObjectBuilderService;
import org.example.util.validator.ObjectBuilderValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/builder_objects")
@Log4j2
public class ObjectsBuilderController {
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";
    private final
    LayoutService layoutService;
    private final
    ObjectBuilderService objectBuilderService;
    private final
    MinioService minioService;
    private final
    ImagesForObjectService imagesForObjectService;
    private final
    ObjectBuilderValidator objectBuilderValidator;

    public ObjectsBuilderController(ObjectBuilderService objectBuilderService, LayoutService layoutService, MinioService minioService, ImagesForObjectService imagesForObjectService, ObjectBuilderValidator objectBuilderValidator) {
        this.objectBuilderService = objectBuilderService;
        this.layoutService = layoutService;
        this.minioService = minioService;
        this.imagesForObjectService = imagesForObjectService;
        this.objectBuilderValidator = objectBuilderValidator;
    }


    @GetMapping("/filter")
    @ResponseBody
    public Page<BuilderObject> showPageObjectBuilder(@ModelAttribute ObjectBuilderDtoSearch objectBuilderDto
            , @RequestParam(name = "page", defaultValue = "0") Integer numberPage, @RequestParam Integer numberOfElement) {
        Pageable pageable = PageRequest.of(numberPage, numberOfElement);
        Page<BuilderObject> pageElements = objectBuilderService.findBuilderObjectsByCriteria(
                objectBuilderDto.getName(),
                objectBuilderDto.getDistrict(),
                objectBuilderDto.getTopozone(),
                objectBuilderDto.getStreet(),
                objectBuilderDto.getFloorQuantity(),
                objectBuilderDto.getMinPrice(),
                pageable);
        return pageElements;
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("builderObjectsActive", true);
    }

    @GetMapping
    public ModelAndView ObjectsBuilderShow() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_builders/objectsBuilders");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView ObjectsBuilderCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_builders/newObjectBuilder");
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> ObjectsBuilderCreate(@PathVariable Integer id) {
        objectBuilderService.deleteById(id);
        return ResponseEntity.ok().body("Object by builder with ID " + id + " successfully deleted");
    }

    @PostMapping("/create")
    public ResponseEntity<?> ObjectsBuilderCreatePost(@Valid @ModelAttribute ObjectBuilderDto objectBuilderDto, BindingResult bindingResult)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException
            , NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        objectBuilderValidator.validate(objectBuilderDto, bindingResult);

        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        BuilderObject builderObject = ObjectBuilderMapper.INSTANCE.toEntity(objectBuilderDto);
        objectBuilderService.saveCreate(objectBuilderDto, builderObject);
        return ResponseEntity.ok().body("The object has been saved");
    }


    @GetMapping("/card/{id}")
    public ModelAndView CardObjectsBuilderShow(@PathVariable Integer id, Model model) {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (objectBuilder.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.setViewName("/objects/object_builders/cardObjectBuilder");
            model.addAttribute("element", objectBuilder.get());
            model.addAttribute("minPrice",
                    (Collections.min(layoutService.findByBuilderObject(objectBuilder.get()).stream().map(s -> s.getPrice()).collect(Collectors.toList()))));
            try {
                String namePhoto = imagesForObjectService.findOneByIdObjectAndTypeObject(objectBuilder.get().getId(), TypeObject.BY_BUILDER).get().getPath();
                byte[] photoData = minioService.getPhoto(namePhoto, imagesBucketName);
                String base64Image = Base64.getEncoder().encodeToString(photoData);
                model.addAttribute("base64Image", base64Image);

                byte[] fileCheckerboard = minioService.getPhoto(objectBuilder.get().getFileCheckerboard(), filesBucketName);
                String base64FileCheckerboard = Base64.getEncoder().encodeToString(fileCheckerboard);
                model.addAttribute("base64FileCheckerboard", base64FileCheckerboard);

                byte[] filePrices = minioService.getPhoto(objectBuilder.get().getFilePrices(), filesBucketName);
                String base64FilePrices = Base64.getEncoder().encodeToString(filePrices);
                model.addAttribute("base64FilePrices", base64FilePrices);

                byte[] fileInstallmentTerms = minioService.getPhoto(objectBuilder.get().getFileInstallmentTerms(), filesBucketName);
                String base64FileInstallmentTerms = Base64.getEncoder().encodeToString(fileInstallmentTerms);
                model.addAttribute("base64FileInstallmentTerms", base64FileInstallmentTerms);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public String EditMainInfoObjectsBuilderShow(@PathVariable Integer id, Model model) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return "/error/404";
        }
        model.addAttribute("objectBuilder", objectBuilder.get());
        List<ImagesForObject> list = imagesForObjectService.findByIdObjectAndTypeObject(objectBuilder.get().getId(), TypeObject.BY_BUILDER);
        List<String> base64ImagesList = new ArrayList<>();
        List<Integer> base64ImagesListSize = new ArrayList<>();
        List<String> base64ImagesListName = new ArrayList<>();
        for (ImagesForObject imagesForObject : list) {
            byte[] filePrices = minioService.getPhoto(imagesForObject.getPath(), imagesBucketName);
            base64ImagesListName.add(imagesForObject.getPath());
            String base64FilePrices = Base64.getEncoder().encodeToString(filePrices);
            base64ImagesList.add(base64FilePrices);
            base64ImagesListSize.add(filePrices.length);
        }
        model.addAttribute("base64Images", base64ImagesList);
        model.addAttribute("base64ImagesListName", base64ImagesListName);
        model.addAttribute("base64ImagesSize", base64ImagesListSize);
        List<Layout> layouts = layoutService.findByBuilderObject(objectBuilder.get());
        model.addAttribute("layouts", layouts);
        List<String> img1 = new ArrayList<>();
        List<String> img2 = new ArrayList<>();
        List<String> img3 = new ArrayList<>();
        List<String> img1Name = new ArrayList<>();
        List<String> img2Name = new ArrayList<>();
        List<String> img3Name = new ArrayList<>();
        //hide img path, send in bytes
        for (Layout layout : layouts) {
            img1.add(minioService.getFileInString(layout.getImg1(), imagesBucketName));
            img2.add(minioService.getFileInString(layout.getImg2(), imagesBucketName));
            img3.add(minioService.getFileInString(layout.getImg3(), imagesBucketName));
            img1Name.add(layout.getImg1());
            img2Name.add(layout.getImg2());
            img3Name.add(layout.getImg3());
        }
        model.addAttribute("img1Name", img1Name);
        model.addAttribute("img2Name", img2Name);
        model.addAttribute("img3Name", img3Name);
        model.addAttribute("img1", img1);
        model.addAttribute("img2", img2);
        model.addAttribute("img3", img3);
        return "/objects/object_builders/editObjectBuilder";
    }

    @Transactional
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> EditMainInfoObjectsBuilderPost(@Valid @ModelAttribute ObjectBuilderDtoEdit objectBuilderDtoEdit, BindingResult bindingResult, @PathVariable Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IllegalAccessException {
        objectBuilderValidator.validateEdit(objectBuilderDtoEdit, bindingResult, id);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        BuilderObject builderObject = objectBuilderService.findById(id).get();
        ObjectBuilderMapper.INSTANCE.toEntity(builderObject, objectBuilderDtoEdit);
        objectBuilderService.saveEdit(objectBuilderDtoEdit, builderObject, id);
        return ResponseEntity.ok().body("ok");
    }


    @GetMapping("/card/downloadFileCheckerboard/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileCheckerboard(@PathVariable Integer id) {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            String fileName = objectBuilder.get().getFileCheckerboard();
            byte[] fileCheckerboard = minioService.getPhoto(fileName, filesBucketName);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            ByteArrayResource resource = new ByteArrayResource(fileCheckerboard);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileCheckerboard.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/card/downloadFileInstallmentTerms/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileInstallmentTerms(@PathVariable Integer id) {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            String fileName = objectBuilder.get().getFileInstallmentTerms();
            byte[] fileInstallmentTerms = minioService.getPhoto(fileName, filesBucketName);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            ByteArrayResource resource = new ByteArrayResource(fileInstallmentTerms);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileInstallmentTerms.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/card/downloadFilePrices/{id}")
    public ResponseEntity<ByteArrayResource> downloadFilePrices(@PathVariable Integer id) {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            String fileName = objectBuilder.get().getFilePrices();
            byte[] filePrices = minioService.getPhoto(fileName, filesBucketName);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            ByteArrayResource resource = new ByteArrayResource(filePrices);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(filePrices.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/for/select")
    @ResponseBody
    public Page<BuilderObject> search(@RequestParam("query") String name,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        Page<BuilderObject> searchResults = objectBuilderService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public BuilderObject getById(@PathVariable Integer id){
        return objectBuilderService.getById(id);
    }
//    @PostMapping("/generate-excel")
//    public ResponseEntity<?> generateExcel(HttpServletResponse response, @RequestBody ArrayList<ObjectBuilderDtoSearch> objectBuilderDTOSearchList) throws IOException {
//        log.info(objectBuilderDTOSearchList);
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Название листа");
//
//        CellStyle boldStyle = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        boldStyle.setFont(font);
//
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Название");
//        headerRow.createCell(1).setCellValue("Район");
//        headerRow.createCell(2).setCellValue("Топозона");
//        headerRow.createCell(3).setCellValue("Улица");
//        headerRow.createCell(4).setCellValue("Этажность");
//        headerRow.createCell(5).setCellValue("Цена от");
//
//        for (int i = 0; i < 6; i++) {
//            headerRow.getCell(i).setCellStyle(boldStyle);
//        }
//        int num=1;
//        for (ObjectBuilderDtoSearch objectBuilderDtoSearch:objectBuilderDTOSearchList){
//            Row row = sheet.createRow(num);
//            row.createCell(0).setCellValue(objectBuilderDtoSearch.getName());
//            row.createCell(1).setCellValue(objectBuilderDtoSearch.getDistrict());
//            row.createCell(2).setCellValue(objectBuilderDtoSearch.getTopozone());
//            row.createCell(3).setCellValue(objectBuilderDtoSearch.getStreet());
//            row.createCell(4).setCellValue(objectBuilderDtoSearch.getFloorQuantity());
//            row.createCell(5).setCellValue(objectBuilderDtoSearch.getMinPrice());
//            num++;
//        }
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        workbook.write(bos);
//        byte[] bytes = bos.toByteArray();
//
//        log.info(bytes);
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("Content-Disposition", "inline; filename=example.xlsx");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(bytes);    }

//
}
