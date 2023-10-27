package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.example.dto.*;
import org.example.entity.BuilderObject;
import org.example.entity.BuilderObjectPromotion;
import org.example.entity.ImagesForObject;
import org.example.entity.Layout;
import org.example.entity.property.type.PropertyBuildStatus;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.entity.property.type.TypeObject;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
@RequestMapping("/builder_objects")
@Log4j2
public class ObjectsBuilderController {
    Integer pageSize = 4;
    String imagesBucketName = "images";
    String filesBucketName = "files";
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
    public Page<BuilderObject> showPageObjectBuilder(@ModelAttribute ObjectBuilderDtoSearch objectBuilderDto, @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
        log.info(objectBuilderDto);
        Pageable pageable = PageRequest.of(numberPage, pageSize);
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
    public ModelAndView ObjectsBuilderShow(Model model, @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_builders/objectsBuilders");
        Page<BuilderObject> pageElements = objectBuilderService.findBuilderObjectsPage(numberPage, pageSize);
        List<BuilderObject> list = pageElements.getContent();
        model.addAttribute("list", list);
        List<Integer> listPrices = new ArrayList<>();
        for (BuilderObject elem : list) {
            List<Integer> prices = layoutService.findByBuilderObject(elem).stream().map(s -> s.getPrice()).collect(Collectors.toList());
            listPrices.add(Collections.min(prices));
        }
        model.addAttribute("listPrice", listPrices);
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("totalPages", pageElements.getTotalPages());
        Long count = pageElements.getTotalElements();
        String panelCount;
        if (count == 0) {
            panelCount = "Нету данных";
        } else {
            panelCount = "Показано " + (pageSize * numberPage + 1) + "-" + (list.size() + (pageSize * numberPage)) + " из " + count;
        }
        log.info(panelCount);
        model.addAttribute("panelCount", panelCount);
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
        return ResponseEntity.ok().body(" Объект от строителя с id " + id + " успешно удален");
    }

    @PostMapping("/create")
    public ResponseEntity ObjectsBuilderCreatePost(@Valid @ModelAttribute ObjectBuilderDto objectBuilderDto, BindingResult bindingResult)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException
            , NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        objectBuilderValidator.validate(objectBuilderDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("error validation");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        log.info("ok validation");
        log.info(objectBuilderDto);
        BuilderObject builderObject = new BuilderObject();
        PropertyObjectAddress propertyObjectAddress = new PropertyObjectAddress();
        propertyObjectAddress.setCity(objectBuilderDto.getCity());
        propertyObjectAddress.setDistrict(objectBuilderDto.getDistrict());
        propertyObjectAddress.setHouseNumber(objectBuilderDto.getHouseNumber());
        propertyObjectAddress.setRegion(objectBuilderDto.getRegion());
        propertyObjectAddress.setSection(objectBuilderDto.getSection());
        propertyObjectAddress.setStreet(objectBuilderDto.getStreet());
        propertyObjectAddress.setZone(objectBuilderDto.getTopozone());
        builderObject.setAddress(propertyObjectAddress);

        builderObject.setName(objectBuilderDto.getNameObject());
        builderObject.setFloorQuantity(objectBuilderDto.getFloorQuantity());
        builderObject.setPhone(objectBuilderDto.getTelephone());
        builderObject.setDescription_builder(objectBuilderDto.getDescription());
        builderObject.setNameCompany(objectBuilderDto.getNameCompany());

        builderObject.setBuildStatus(PropertyBuildStatus.valueOf(objectBuilderDto.getBuildStatus()));


        BuilderObjectPromotion builderObjectPromotion = new BuilderObjectPromotion();
        builderObjectPromotion.setName(objectBuilderDto.getPromotionName());
        builderObjectPromotion.setDescription(objectBuilderDto.getDescriptionPromotion());
        Boolean statusPromotion = Boolean.parseBoolean(objectBuilderDto.getStatusPromotion());
        builderObjectPromotion.setActive(statusPromotion);
        builderObject.setPromotion(builderObjectPromotion);

        String uuidFile = UUID.randomUUID().toString();

        String resultFilename = uuidFile + "." + objectBuilderDto.getPrices().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getPrices(), filesBucketName, resultFilename);
        builderObject.setFilePrices(resultFilename);

        uuidFile = UUID.randomUUID().toString();
        resultFilename = uuidFile + "." + objectBuilderDto.getChessboardFile().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getChessboardFile(), filesBucketName, resultFilename);
        builderObject.setFileCheckerboard(resultFilename);

        uuidFile = UUID.randomUUID().toString();
        resultFilename = uuidFile + "." + objectBuilderDto.getInstallmentTerms().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getInstallmentTerms(), filesBucketName, resultFilename);
        builderObject.setFileInstallmentTerms(resultFilename);


        objectBuilderService.save(builderObject);
        for (LayoutDTO dto : objectBuilderDto.getLayoutDTOList()) {
            Layout layout = new Layout();
            layout.setName(dto.getNameLayout());
            layout.setPrice(dto.getPriceLayout());
            layout.setRoomQuantity(dto.getRoomQuantityLayout());
            layout.setAreaKitchen(dto.getAreaKitchenLayout());
            layout.setAreaLiving(dto.getAreaLivingLayout());
            layout.setAreaTotal(dto.getAreaTotalLayout());
            layout.setActive(dto.getStatusLayout());
            layout.setDescription(dto.getDescriptionLayout());

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg1Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg1Layout(), imagesBucketName, resultFilename);
            layout.setImg1(resultFilename);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg2Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg2Layout(), imagesBucketName, resultFilename);
            layout.setImg2(resultFilename);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg3Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg3Layout(), imagesBucketName, resultFilename);
            layout.setImg3(resultFilename);

            layout.setBuilderObject(builderObject);
            layoutService.save(layout);
        }
        for (MultipartFile file : objectBuilderDto.getFiles()) {
            ImagesForObject imagesForObject = new ImagesForObject();
            imagesForObject.setIdObject(builderObject.getId());
            imagesForObject.setTypeObject(TypeObject.BY_BUILDER);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            minioService.putMultipartFile(file, imagesBucketName, resultFilename);
            imagesForObject.setPath(resultFilename);

            imagesForObjectService.save(imagesForObject);
        }

        return ResponseEntity.ok().body("Сохранено");
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
    public String EditMainInfoObjectsBuilderShow(@PathVariable Integer id, Model model) throws MinioException,
            NoSuchAlgorithmException, IOException, InvalidKeyException {
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
        log.info("size" + layouts.size());
        List<String> img1 = new ArrayList<>();
        List<String> img2 = new ArrayList<>();
        List<String> img3 = new ArrayList<>();
        List<String> img1Name = new ArrayList<>();
        List<String> img2Name = new ArrayList<>();
        List<String> img3Name = new ArrayList<>();
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/edit/{id}")
    public ResponseEntity EditMainInfoObjectsBuilderPost(@Valid @ModelAttribute ObjectBuilderDtoEdit objectBuilderDtoEdit, BindingResult bindingResult, @PathVariable Integer id)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException
            , NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(objectBuilderDtoEdit.getLayoutDTOList());
        log.info(objectBuilderDtoEdit.getOldFiles());
        log.info(objectBuilderDtoEdit.getOldFiles().size());

        objectBuilderValidator.validateEdit(objectBuilderDtoEdit, bindingResult, id);
        try {
            if (!objectBuilderDtoEdit.getFiles().isEmpty()) {
                log.info(objectBuilderDtoEdit.getFiles().stream().map(s -> s.getOriginalFilename()).collect(Collectors.toList()));
                log.info(objectBuilderDtoEdit.getFiles().size());
            }
        } catch (NullPointerException e) {
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }

        BuilderObject builderObject = objectBuilderService.findById(id).get();
        PropertyObjectAddress propertyObjectAddress = new PropertyObjectAddress();
        propertyObjectAddress.setCity(objectBuilderDtoEdit.getCity());
        propertyObjectAddress.setDistrict(objectBuilderDtoEdit.getDistrict());
        propertyObjectAddress.setHouseNumber(objectBuilderDtoEdit.getHouseNumber());
        propertyObjectAddress.setRegion(objectBuilderDtoEdit.getRegion());
        propertyObjectAddress.setSection(objectBuilderDtoEdit.getSection());
        propertyObjectAddress.setStreet(objectBuilderDtoEdit.getStreet());
        propertyObjectAddress.setZone(objectBuilderDtoEdit.getTopozone());
        builderObject.setAddress(propertyObjectAddress);

        builderObject.setName(objectBuilderDtoEdit.getNameObject());
        builderObject.setFloorQuantity(objectBuilderDtoEdit.getFloorQuantity());
        builderObject.setPhone(objectBuilderDtoEdit.getTelephone());
        builderObject.setDescription_builder(objectBuilderDtoEdit.getDescription());
        builderObject.setNameCompany(objectBuilderDtoEdit.getNameCompany());

        builderObject.setBuildStatus(PropertyBuildStatus.valueOf(objectBuilderDtoEdit.getBuildStatus()));


        BuilderObjectPromotion builderObjectPromotion = new BuilderObjectPromotion();
        builderObjectPromotion.setName(objectBuilderDtoEdit.getPromotionName());
        builderObjectPromotion.setDescription(objectBuilderDtoEdit.getDescriptionPromotion());
        Boolean statusPromotion = Boolean.parseBoolean(objectBuilderDtoEdit.getStatusPromotion());
        builderObjectPromotion.setActive(statusPromotion);
        builderObject.setPromotion(builderObjectPromotion);

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename;

        if (objectBuilderDtoEdit.getPrices() != null && objectBuilderDtoEdit.getPrices().isPresent()) {
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getPrices().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getPrices().get(), filesBucketName, resultFilename);
            builderObject.setFilePrices(resultFilename);
        }

        if (objectBuilderDtoEdit.getChessboardFile() != null && objectBuilderDtoEdit.getChessboardFile().isPresent()) {
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getChessboardFile().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getChessboardFile().get(), filesBucketName, resultFilename);
            builderObject.setFileCheckerboard(resultFilename);
        }

        if (objectBuilderDtoEdit.getInstallmentTerms() != null && objectBuilderDtoEdit.getInstallmentTerms().isPresent()) {
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getInstallmentTerms().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getInstallmentTerms().get(), filesBucketName, resultFilename);
            builderObject.setFileInstallmentTerms(resultFilename);
        }


        objectBuilderService.save(builderObject);

        layoutService.deleteAllByBuilderObject(builderObject);

        for (LayoutDTOEdit dto : objectBuilderDtoEdit.getLayoutDTOList()) {
            Layout layout = new Layout();
            layout.setName(dto.getNameLayout());
            layout.setPrice(dto.getPriceLayout());
            layout.setRoomQuantity(dto.getRoomQuantityLayout());
            layout.setAreaKitchen(dto.getAreaKitchenLayout());
            layout.setAreaLiving(dto.getAreaLivingLayout());
            layout.setAreaTotal(dto.getAreaTotalLayout());
            layout.setActive(dto.getStatusLayout());
            layout.setDescription(dto.getDescriptionLayout());

            if (dto.getImg1Layout() != null && !dto.getImg1Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg1Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg1Layout().get(), imagesBucketName, resultFilename);
                layout.setImg1(resultFilename);
            } else {
                layout.setImg1(dto.getImg1Old());
            }

            if (dto.getImg2Layout() != null && !dto.getImg2Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg2Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg2Layout().get(), imagesBucketName, resultFilename);
                layout.setImg2(resultFilename);
            } else {
                layout.setImg2(dto.getImg2Old());
            }

            if (dto.getImg3Layout() != null && !dto.getImg3Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg3Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg3Layout().get(), imagesBucketName, resultFilename);
                layout.setImg3(resultFilename);
            } else {
                layout.setImg3(dto.getImg3Old());
            }

            layout.setBuilderObject(builderObject);
            layoutService.save(layout);
        }

        List<ImagesForObject> namesImages = imagesForObjectService.findByIdObjectAndTypeObject(id, TypeObject.BY_BUILDER).stream()
                .filter(path -> !objectBuilderDtoEdit.getOldFiles().contains(path.getPath()))
                .collect(Collectors.toList());
        log.info(namesImages);
        namesImages.stream().forEach(s -> imagesForObjectService.deleteById(s.getIdImage()));
        namesImages.stream().forEach(s -> {
            try {
                minioService.deleteImg(s.getPath(), imagesBucketName);
            } catch (ErrorResponseException e) {
                throw new RuntimeException(e);
            } catch (InsufficientDataException e) {
                throw new RuntimeException(e);
            } catch (InternalException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidResponseException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (ServerException e) {
                throw new RuntimeException(e);
            } catch (XmlParserException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        for (MultipartFile file : objectBuilderDtoEdit.getFiles()) {
            ImagesForObject imagesForObject = new ImagesForObject();
            imagesForObject.setIdObject(builderObject.getId());
            imagesForObject.setTypeObject(TypeObject.BY_BUILDER);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            minioService.putMultipartFile(file, imagesBucketName, resultFilename);
            imagesForObject.setPath(resultFilename);

            imagesForObjectService.save(imagesForObject);
        }


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
            ByteArrayResource resource = new ByteArrayResource(fileCheckerboard);
            return getByteArrayResourceResponseEntity(fileName, fileCheckerboard, resource);
        } catch (Exception e) {
            log.error(e);
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
            byte[] fileCheckerboard = minioService.getPhoto(fileName, filesBucketName);
            ByteArrayResource resource = new ByteArrayResource(fileCheckerboard);
            return getByteArrayResourceResponseEntity(fileName, fileCheckerboard, resource);
        } catch (Exception e) {
            log.error(e);
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
            byte[] file = minioService.getPhoto(fileName, filesBucketName);
            ByteArrayResource resource = new ByteArrayResource(file);
            return getByteArrayResourceResponseEntity(fileName, file, resource);
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.notFound().build();
        }
    }

    @NotNull
    private ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String fileName,
                                                                                 byte[] file, ByteArrayResource resource) {
        HttpHeaders headers = new HttpHeaders();
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length)
                .body(resource);
    }

    @PostMapping("/generate-excel")
    public ResponseEntity generateExcel(HttpServletResponse response, @RequestBody ArrayList<ObjectBuilderDtoSearch> objectBuilderDTOSearchList) throws IOException {
        log.info(objectBuilderDTOSearchList);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Название листа");

        CellStyle boldStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        boldStyle.setFont(font);

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Название");
        headerRow.createCell(1).setCellValue("Район");
        headerRow.createCell(2).setCellValue("Топозона");
        headerRow.createCell(3).setCellValue("Улица");
        headerRow.createCell(4).setCellValue("Этажность");
        headerRow.createCell(5).setCellValue("Цена от");

        for (int i = 0; i < 6; i++) {
            headerRow.getCell(i).setCellStyle(boldStyle);
        }
        int num=1;
        for (ObjectBuilderDtoSearch objectBuilderDtoSearch:objectBuilderDTOSearchList){
            Row row = sheet.createRow(num);
            row.createCell(0).setCellValue(objectBuilderDtoSearch.getName());
            row.createCell(1).setCellValue(objectBuilderDtoSearch.getDistrict());
            row.createCell(2).setCellValue(objectBuilderDtoSearch.getTopozone());
            row.createCell(3).setCellValue(objectBuilderDtoSearch.getStreet());
            row.createCell(4).setCellValue(objectBuilderDtoSearch.getFloorQuantity());
            row.createCell(5).setCellValue(objectBuilderDtoSearch.getMinPrice());
            num++;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] bytes = bos.toByteArray();

        log.info(bytes);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "inline; filename=example.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);    }
}
