package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.InvestorObjectDtoSearch;
import org.example.dto.ObjectBuilderDtoSearch;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.entity.BuilderObject;
import org.example.entity.ImagesForObject;
import org.example.entity.Realtor;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.type.TypeObject;
import org.example.mapper.ObjectInvestorMapper;
import org.example.service.*;
import org.example.service.specification.InvestorObjectSpecification;
import org.example.util.ResponseEntityHelper;
import org.example.util.validator.ObjectInvestorValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.util.ResponseEntityHelper.getResponseEntity;

@Controller
@RequestMapping("/investor_objects")
@Log4j2
public class ObjectsInvestorController {
    private final
    PropertyInvestorObjectService propertyInvestorObjectService;
    private final
    RealtorServiceImpl realtorService;
    private final
    MinioService minioService;
    private final
    ImagesForObjectService imagesForObjectService;
    private int pageSize = 2;
    private final
    ObjectInvestorValidator objectInvestorValidator;


    public ObjectsInvestorController(PropertyInvestorObjectService propertyInvestorObjectService, RealtorServiceImpl realtorService, MinioService minioService, ImagesForObjectService imagesForObjectService, ObjectInvestorValidator objectInvestorValidator, ObjectBuilderService objectBuilderService) {
        this.propertyInvestorObjectService = propertyInvestorObjectService;
        this.realtorService = realtorService;
        this.minioService = minioService;
        this.imagesForObjectService = imagesForObjectService;
        this.objectInvestorValidator = objectInvestorValidator;
        this.objectBuilderService = objectBuilderService;
    }

    @GetMapping
    public ModelAndView showObjectsInvestors() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/objectsInvestors");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView newObjectsInvestorControllerPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/newObjectInvestor");
        return modelAndView;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> ObjectsBuilderCreate(@PathVariable Integer id) {
        propertyInvestorObjectService.deleteById(id);
        return ResponseEntity.ok().body(" Объект от строителя с id " + id + " успешно удален");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("investorObjectsActive", true);
    }
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity newObjectsInvestorControllerPost(@Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertyInvestorObjectDTO);
        PropertyInvestorObject propertyInvestorObject = ObjectInvestorMapper.INSTANCE.toEntity(propertyInvestorObjectDTO);
        log.info(propertyInvestorObject);
        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getById(propertyInvestorObjectDTO.getEmployeeCode());
        } catch (EntityNotFoundException | NullPointerException ex ) {
            bindingResult.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        objectInvestorValidator.validate(propertyInvestorObjectDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertyInvestorObject.setRealtor(realtor);
        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertyInvestorObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertyInvestorObject.setFiles(nameFiles);
        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertyInvestorObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertyInvestorObject.setPictures(namePictures);
        propertyInvestorObject.setBuilderObject(objectBuilderService.findById(propertyInvestorObjectDTO.getResidentialComplexId()).get());
        propertyInvestorObjectService.save(propertyInvestorObject);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/filter")
    @ResponseBody
    public Page<PropertyInvestorObject> showPageObjectBuilder(@ModelAttribute InvestorObjectDtoSearch objectDto
            , @RequestParam(name = "page", defaultValue = "0") Integer numberPage, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info(objectDto);
        Pageable pageable = PageRequest.of(numberPage, pageSize);
        Page<PropertyInvestorObject> pageElements = propertyInvestorObjectService.findBuilderObjectsByCriteria(
                objectDto.getDistrict(),
                objectDto.getNumberRooms(),
                objectDto.getMinFloor(),
                objectDto.getMaxFloor(),
                objectDto.getMinPrice(),
                objectDto.getMaxPrice(),
                objectDto.getTopozone(),
                objectDto.getResidentialComplexId(),
                objectDto.getMinNumberFloors(),
                objectDto.getMaxNumberFloors(),
                objectDto.getMinArea(),
                objectDto.getMaxArea(),
                objectDto.getStreet(),
                objectDto.getLastContactDate(),
                pageable);
        return pageElements;
    }

    String imagesBucketName = "images";
    String filesBucketName = "files";
    private final
    ObjectBuilderService objectBuilderService;

    @GetMapping("/edit/{id}")
    public ModelAndView EditMainInfoObjectsInvestorShow(@PathVariable Integer id) throws MinioException,
            NoSuchAlgorithmException, IOException, InvalidKeyException {
        ModelAndView modelAndView = new ModelAndView();
        Optional<PropertyInvestorObject> objectBuilder = propertyInvestorObjectService.findById(id);
        if (objectBuilder.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.addObject("element", objectBuilder.get());
            modelAndView.addObject("elementObjectBuilder", objectBuilderService.findById(objectBuilder.get().getResidentialComplexId()).get().getName());
            modelAndView.setViewName("/objects/object_investor/editObjectInvestor");
            List<String> base64ImagesList = new ArrayList<>();
            List<Integer> base64ImagesListSize = new ArrayList<>();
            List<String> base64ImagesListName = new ArrayList<>();
            for (String imagesForObject : objectBuilder.get().getPictures()) {
                byte[] pictures = minioService.getPhoto(imagesForObject, imagesBucketName);
                base64ImagesListName.add(imagesForObject);
                String base64FilePrices = Base64.getEncoder().encodeToString(pictures);
                base64ImagesList.add(base64FilePrices);
                base64ImagesListSize.add(pictures.length);
            }
            modelAndView.addObject("base64Images", base64ImagesList);
            modelAndView.addObject("base64ImagesListName", base64ImagesListName);
            modelAndView.addObject("base64ImagesSize", base64ImagesListSize);


        }
        return modelAndView;
    }


    @ResponseBody
    @PostMapping("/edit/{id}")
    public ResponseEntity editObjectsInvestorControllerPost(@PathVariable Integer id,@Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertyInvestorObjectDTO);
        PropertyInvestorObject propertyInvestorObject= propertyInvestorObjectService.findById(id).get();

        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getById(propertyInvestorObjectDTO.getEmployeeCode());
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        objectInvestorValidator.validateEdit(propertyInvestorObjectDTO, bindingResult, propertyInvestorObject.getObjectCode());
        if(propertyInvestorObjectDTO.getOldFiles()==null){
            propertyInvestorObjectDTO.setOldFiles(new ArrayList<>());
        }


        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        ResponseEntityHelper.streamFiles(propertyInvestorObject.getFiles(), propertyInvestorObjectDTO.getOldFiles(), log, minioService, filesBucketName, propertyInvestorObject.getPictures(), propertyInvestorObjectDTO.getOldPictures(), imagesBucketName, propertyInvestorObjectDTO, propertyInvestorObject);


        ObjectInvestorMapper.INSTANCE.toOldEntity(propertyInvestorObject, propertyInvestorObjectDTO);
        propertyInvestorObject.setBuilderObject(objectBuilderService.findById(propertyInvestorObjectDTO.getResidentialComplexId()).get());

        propertyInvestorObject.setRealtor(realtor);


        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertyInvestorObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertyInvestorObjectDTO.getOldFiles().addAll(nameFiles);
        propertyInvestorObject.setFiles(propertyInvestorObjectDTO.getOldFiles());


        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertyInvestorObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertyInvestorObjectDTO.getOldPictures().addAll(namePictures);
        propertyInvestorObject.setPictures(propertyInvestorObjectDTO.getOldPictures());




        propertyInvestorObjectService.save(propertyInvestorObject);

        return ResponseEntity.ok().body("ok");
    }



    @GetMapping("/files/{id}")
    public ResponseEntity getFiles(@PathVariable Integer id) {
        Optional<PropertyInvestorObject> objectBuilder = propertyInvestorObjectService.findById(id);
        List<byte[]> fileDataList;
        return getResponseEntity(objectBuilder.isEmpty(), objectBuilder.get().getFiles(), minioService, filesBucketName, objectBuilder);
    }




    @GetMapping("/card/{id}")
    public ModelAndView CardObjectsBuilderShow(@PathVariable Integer id, Model model) {
        Optional<PropertyInvestorObject> entity = propertyInvestorObjectService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (entity.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.setViewName("/objects/object_investor/cardObjectInvestor");
            model.addAttribute("element", entity.get());
            try {
                String namePhoto = entity.get().getPictures().get(0);
                byte[] photoData = minioService.getPhoto(namePhoto, imagesBucketName);
                String base64Image = Base64.getEncoder().encodeToString(photoData);
                model.addAttribute("base64Image", base64Image);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return modelAndView;
    }


    @GetMapping("/for/select")
    @ResponseBody
    public Page<PropertyInvestorObject> search(@RequestParam("query") String name,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        Page<PropertyInvestorObject> searchResults = propertyInvestorObjectService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }

}
