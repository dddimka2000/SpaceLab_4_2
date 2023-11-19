package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.InvestorObjectDtoSearch;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.BuilderObject;
import org.example.entity.Realtor;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.mapper.ObjectSecondaryMapper;
import org.example.mapper.ObjectSecondaryMapper;
import org.example.service.MinioService;
import org.example.service.ObjectBuilderService;
import org.example.service.PropertySecondaryObjectService;
import org.example.service.RealtorServiceImpl;
import org.example.util.validator.SecondaryObjectValidator;
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

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/secondary_objects")
@Log4j2
public class SecondaryObjectsController {
    public SecondaryObjectsController(RealtorServiceImpl realtorService, MinioService minioService) {
        this.realtorService = realtorService;
        this.minioService = minioService;
    }

    @GetMapping
    public ModelAndView SecondaryObjectsShow(Model model, @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/secondary_objects/secondaryObjects");
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView SecondaryObjectsCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/secondary_objects/secondaryObjectsCreate");
        return modelAndView;
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("secondaryPropertyActive", true);
    }

    final
    RealtorServiceImpl realtorService;
    @Autowired
    SecondaryObjectValidator secondaryObjectValidator;
    @Autowired
    PropertySecondaryObjectService propertySecondaryObjectService;
    final
    MinioService minioService;
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity newObjectsSecondaryControllerPost(@Valid @ModelAttribute PropertySecondaryObjectDTO propertySecondaryObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertySecondaryObjectDTO);
        PropertySecondaryObject propertySecondaryObject = ObjectSecondaryMapper.INSTANCE.toEntity(propertySecondaryObjectDTO);
        log.info(propertySecondaryObject);
        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getById(propertySecondaryObjectDTO.getEmployeeCode());
        } catch (EntityNotFoundException | NullPointerException ex ) {
            bindingResult.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        secondaryObjectValidator.validate(propertySecondaryObjectDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertySecondaryObject.setRealtor(realtor);
        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertySecondaryObject.setFiles(nameFiles);
        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertySecondaryObject.setPictures(namePictures);
        propertySecondaryObjectService.save(propertySecondaryObject);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> ObjectsBuilderCreate(@PathVariable Integer id) {
        propertySecondaryObjectService.deleteById(id);
        return ResponseEntity.ok().body(" Объект от строителя с id " + id + " успешно удален");
    }
    @GetMapping("/card/{id}")
    public ModelAndView CardPropertySecondaryObjectShow(@PathVariable Integer id, Model model) {
        Optional<PropertySecondaryObject> entity = propertySecondaryObjectService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (entity.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.setViewName("/objects/secondary_objects/secondaryObjectsCard");
            model.addAttribute("element", entity.get());
            try {
                String namePhoto = entity.get().getPictures().get(0);
                log.info(entity.get().getPictures());
                log.info(namePhoto);
                byte[] photoData = minioService.getPhoto(namePhoto, imagesBucketName);
                String base64Image = Base64.getEncoder().encodeToString(photoData);
                model.addAttribute("base64Image", base64Image);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return modelAndView;
    }
    @Autowired
    ObjectBuilderService objectBuilderService;
    @GetMapping("/edit/{id}")
    public ModelAndView EditMainInfoPropertySecondaryObjectShow(@PathVariable Integer id) throws MinioException,
            NoSuchAlgorithmException, IOException, InvalidKeyException {
        ModelAndView modelAndView = new ModelAndView();
        Optional<PropertySecondaryObject> objectBuilder = propertySecondaryObjectService.findById(id);
        if (objectBuilder.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            log.info(objectBuilder.get().getFiles());
            modelAndView.addObject("element", objectBuilder.get());
            modelAndView.addObject("elementObjectBuilder", objectBuilderService.findById(objectBuilder.get().getResidentialComplexId()).get().getName());
            modelAndView.setViewName("/objects/secondary_objects/secondaryObjectsEdit");
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
    public ResponseEntity editObjectsSecondaryControllerPost(@PathVariable Integer id, @Valid @ModelAttribute PropertySecondaryObjectDTO propertySecondaryObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertySecondaryObjectDTO);
        PropertySecondaryObject propertySecondaryObject= propertySecondaryObjectService.findById(id).get();

        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getById(propertySecondaryObjectDTO.getEmployeeCode());
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        secondaryObjectValidator.validateEdit(propertySecondaryObjectDTO, bindingResult, propertySecondaryObject.getObjectCode());
        if(propertySecondaryObjectDTO.getOldFiles()==null){
            propertySecondaryObjectDTO.setOldFiles(new ArrayList<>());
        }


        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        List<String> namesFilesDelete = propertySecondaryObject.getFiles().stream()
                .filter(path -> !propertySecondaryObjectDTO.getOldFiles().contains(path))
                .collect(Collectors.toList());
        log.info(namesFilesDelete);
        namesFilesDelete.stream().forEach(s-> {
            try {
                minioService.deleteImg(s,filesBucketName);
            } catch (ErrorResponseException e) {
                throw new RuntimeException(e);
            }
            catch (InsufficientDataException e) {
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

        List<String> namesPicturesDelete = propertySecondaryObject.getPictures().stream()
                .filter(path -> !propertySecondaryObjectDTO.getOldPictures().contains(path))
                .collect(Collectors.toList());
        log.info(namesPicturesDelete);
        namesPicturesDelete.stream().forEach(s-> {
            try {
                minioService.deleteImg(s,imagesBucketName);
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




        ObjectSecondaryMapper.INSTANCE.toOldEntity(propertySecondaryObject, propertySecondaryObjectDTO);
        propertySecondaryObject.setRealtor(realtor);


        List<String> nameFiles = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getFiles()) {
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertySecondaryObjectDTO.getOldFiles().addAll(nameFiles);
        propertySecondaryObject.setFiles(propertySecondaryObjectDTO.getOldFiles());


        List<String> namePictures = new ArrayList<>();
        for (MultipartFile multipartFile : propertySecondaryObjectDTO.getPictures()) {
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertySecondaryObjectDTO.getOldPictures().addAll(namePictures);
        propertySecondaryObject.setPictures(propertySecondaryObjectDTO.getOldPictures());


        propertySecondaryObjectService.save(propertySecondaryObject);

        return ResponseEntity.ok().body("ok");
    }
    String imagesBucketName = "images";
    String filesBucketName = "files";
    @GetMapping("/files/{id}")
    public ResponseEntity getFiles(@PathVariable Integer id) {
        Optional<PropertySecondaryObject> objectBuilder = propertySecondaryObjectService.findById(id);
        List<byte[]> fileDataList = new ArrayList<>();
        if (objectBuilder.isEmpty()) {
            ResponseEntity.badRequest();
        }
        fileDataList = objectBuilder.get().getFiles().stream().map(s -> {
            try {
                return minioService.getPhoto(s, filesBucketName);
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
        }).collect(Collectors.toList());

        List<String> base64FileList = fileDataList.stream()
                .map(Base64.getEncoder()::encodeToString)
                .toList();

        return ResponseEntity.ok().body(base64FileList);
    }
    @GetMapping("/filter")
    @ResponseBody
    public Page<PropertySecondaryObject> showPageObjectBuilder(@ModelAttribute InvestorObjectDtoSearch objectDto
            , @RequestParam(name = "page", defaultValue = "0") Integer numberPage, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info(objectDto);
        Pageable pageable = PageRequest.of(numberPage, pageSize);
        Page<PropertySecondaryObject> pageElements = propertySecondaryObjectService.findBuilderObjectsByCriteria(
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
    @GetMapping("/for/select")
    @ResponseBody
    public Page<PropertySecondaryObject> search(@RequestParam("query") String name,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size) {
        Page<PropertySecondaryObject> searchResults = propertySecondaryObjectService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }
}
