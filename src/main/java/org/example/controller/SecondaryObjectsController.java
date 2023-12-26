package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.InvestorObjectDtoSearch;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.property.PropertySecondaryObject;
import org.example.mapper.ObjectSecondaryMapper;
import org.example.service.MinioService;
import org.example.service.ObjectBuilderService;
import org.example.service.PropertySecondaryObjectService;
import org.example.service.RealtorServiceImpl;
import org.example.util.validator.SecondaryObjectValidator;
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
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/secondary_objects")
@Log4j2
public class SecondaryObjectsController {
    String imagesBucketName = "images";
    String filesBucketName = "files";
    final
    RealtorServiceImpl realtorService;
    final
    SecondaryObjectValidator secondaryObjectValidator;
    final
    PropertySecondaryObjectService propertySecondaryObjectService;
    final
    MinioService minioService;

    final
    ObjectBuilderService objectBuilderService;


    public SecondaryObjectsController(RealtorServiceImpl realtorService, MinioService minioService, PropertySecondaryObjectService propertySecondaryObjectService, SecondaryObjectValidator secondaryObjectValidator, ObjectBuilderService objectBuilderService) {
        this.realtorService = realtorService;
        this.minioService = minioService;
        this.propertySecondaryObjectService = propertySecondaryObjectService;
        this.secondaryObjectValidator = secondaryObjectValidator;
        this.objectBuilderService = objectBuilderService;
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

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> newObjectsSecondaryControllerPost(@Valid @ModelAttribute PropertySecondaryObjectDTO propertySecondaryObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertySecondaryObject propertySecondaryObject = ObjectSecondaryMapper.INSTANCE.toEntity(propertySecondaryObjectDTO);
        secondaryObjectValidator.validate(propertySecondaryObjectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("error validation");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertySecondaryObjectService.saveCreate(propertySecondaryObject,propertySecondaryObjectDTO);
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> ObjectsBuilderCreate(@PathVariable Integer id) {
        propertySecondaryObjectService.deleteById(id);
        return ResponseEntity.ok().body("Secondary real estate object with ID " + id + " has been successfully deleted");
    }

    @GetMapping("/card/{id}")
    public ModelAndView CardPropertySecondaryObjectShow(@PathVariable Integer id) {
        Optional<PropertySecondaryObject> entity = propertySecondaryObjectService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (entity.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.setViewName("/objects/secondary_objects/secondaryObjectsCard");
            modelAndView.addObject("element", entity.get());
            try {
                String namePhoto = entity.get().getPictures().get(0);
                byte[] photoData = minioService.getPhoto(namePhoto, imagesBucketName);
                String base64Image = Base64.getEncoder().encodeToString(photoData);
                modelAndView.addObject("base64Image", base64Image);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView EditMainInfoPropertySecondaryObjectShow(@PathVariable Integer id) throws MinioException,
            NoSuchAlgorithmException, IOException, InvalidKeyException {
        ModelAndView modelAndView = new ModelAndView();
        Optional<PropertySecondaryObject> objectBuilder = propertySecondaryObjectService.findById(id);
        if (objectBuilder.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
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
    public ResponseEntity<?>  editObjectsSecondaryControllerPost(@PathVariable Integer id, @Valid @ModelAttribute PropertySecondaryObjectDTO propertySecondaryObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertySecondaryObjectDTO);
        PropertySecondaryObject propertySecondaryObject = propertySecondaryObjectService.findById(id).get();

        secondaryObjectValidator.validateEdit(propertySecondaryObjectDTO, bindingResult, propertySecondaryObject.getObjectCode());
        if (propertySecondaryObjectDTO.getOldFiles() == null) {
            propertySecondaryObjectDTO.setOldFiles(new ArrayList<>());
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertySecondaryObjectService.saveEdit(propertySecondaryObject, propertySecondaryObjectDTO);
        return ResponseEntity.ok().body("ok");
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<?>  getFiles(@PathVariable Integer id) {
        Optional<PropertySecondaryObject> objectBuilder = propertySecondaryObjectService.findById(id);
        return minioService.getResponseEntity(objectBuilder.isEmpty(), objectBuilder.get().getFiles(), filesBucketName);
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
