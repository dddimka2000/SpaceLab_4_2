package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.LayoutDTO;
import org.example.dto.ObjectBuilderDto;
import org.example.entity.BuilderObject;
import org.example.entity.BuilderObjectPromotion;
import org.example.entity.ImagesForObject;
import org.example.entity.Layout;
import org.example.service.ImagesForObjectService;
import org.example.service.LayoutService;
import org.example.service.MinioService;
import org.example.service.ObjectBuilderService;
import org.example.util.TypeObject;
import org.example.util.property.PropertyBuildStatus;
import org.example.util.property.PropertyObjectAddress;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/objects/byBuilder")
@Log4j2
public class ObjectsBuilderController {
    Integer pageSize = 10;
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

    public ObjectsBuilderController(ObjectBuilderService objectBuilderService, LayoutService layoutService, MinioService minioService, ImagesForObjectService imagesForObjectService) {
        this.objectBuilderService = objectBuilderService;
        this.layoutService = layoutService;
        this.minioService = minioService;
        this.imagesForObjectService = imagesForObjectService;
    }

    @GetMapping
    public String ObjectsBuilderShow(Model model, @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
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
        return "/objects/object_builders/objectsBuilders";
    }

    @GetMapping("/create")
    public String ObjectsBuilderCreate() {

        return "/objects/object_builders/newObjectBuilder";
    }

    @PostMapping("/create")
    public ResponseEntity ObjectsBuilderCreatePost(@Valid @ModelAttribute ObjectBuilderDto objectBuilderDto, BindingResult bindingResult)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException
            , NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }

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
            imagesForObject.setTypeObject(TypeObject.ByBuilder);

            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            minioService.putMultipartFile(file, imagesBucketName, resultFilename);
            imagesForObject.setPath(resultFilename);

            imagesForObjectService.save(imagesForObject);
        }

        return ResponseEntity.ok().body("Сохранено");
    }

    @GetMapping("/card/{id}")
    public String CardObjectsBuilderShow(@PathVariable Integer id, Model model) {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return "/error/404";
        }

        model.addAttribute("element", objectBuilder.get());
        model.addAttribute("minPrice",
                (Collections.min(layoutService.findByBuilderObject(objectBuilder.get()).stream().map(s -> s.getPrice()).collect(Collectors.toList()))));
        try {
            String namePhoto = imagesForObjectService.findOneByIdObjectAndTypeObject(objectBuilder.get().getId(), TypeObject.ByBuilder).get().getPath();
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
        return "/objects/object_builders/cardObjectBuilder";
    }


    @GetMapping("/edit/{id}")
    public String EditMainInfoObjectsBuilderShow(@PathVariable Integer id, Model model) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Optional<BuilderObject> objectBuilder = objectBuilderService.findById(id);
        if (objectBuilder.isEmpty()) {
            return "/error/404";
        }
        model.addAttribute("objectBuilder", objectBuilder.get());

        List<ImagesForObject> list = imagesForObjectService.findByIdObjectAndTypeObject(objectBuilder.get().getId(), TypeObject.ByBuilder);

        List<String> base64ImagesList = new ArrayList<>();
        List<Integer> base64ImagesListSize = new ArrayList<>();
        for (ImagesForObject imagesForObject : list) {
            byte[] filePrices = minioService.getPhoto(imagesForObject.getPath(), imagesBucketName);
            String base64FilePrices = Base64.getEncoder().encodeToString(filePrices);
            base64ImagesList.add(base64FilePrices);
            base64ImagesListSize.add(filePrices.length);
        }
        model.addAttribute("base64Images", base64ImagesList);
        model.addAttribute("base64ImagesSize", base64ImagesListSize);
        List<Layout> layouts=layoutService.findByBuilderObject(objectBuilder.get());
        model.addAttribute("layouts",layouts);
        model.addAttribute("img1",layouts.stream().map(layout -> {
            try {
                return minioService.getFileInString(layout.getImg1(),imagesBucketName);
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
        }).collect(Collectors.toList()));
        model.addAttribute("img2",layouts.stream().map(layout -> {
            try {
                return minioService.getFileInString(layout.getImg2(),imagesBucketName);
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
        }).collect(Collectors.toList()));
        model.addAttribute("img3",layouts.stream().map(layout -> {
            try {
                return minioService.getFileInString(layout.getImg3(),imagesBucketName);
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
        }).collect(Collectors.toList()));
        return "/objects/object_builders/editObjectBuilder";
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
    private ResponseEntity<ByteArrayResource> getByteArrayResourceResponseEntity(String fileName, byte[] file, ByteArrayResource resource) {
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
}
