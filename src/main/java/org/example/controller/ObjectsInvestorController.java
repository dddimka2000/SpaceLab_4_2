package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.InvestorObjectDtoSearch;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.PropertyInvestorObject;
import org.example.mapper.ObjectInvestorMapper;
import org.example.service.MinioService;
import org.example.service.ObjectBuilderService;
import org.example.service.PropertyInvestorObjectService;
import org.example.service.RealtorServiceImpl;
import org.example.util.validator.ObjectInvestorValidator;
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
@RequestMapping("/investor_objects")
@Log4j2
public class ObjectsInvestorController {
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";
    private final
    PropertyInvestorObjectService propertyInvestorObjectService;
    private final
    RealtorServiceImpl realtorService;
    private final
    MinioService minioService;
    private final
    ObjectInvestorValidator objectInvestorValidator;


    public ObjectsInvestorController(PropertyInvestorObjectService propertyInvestorObjectService, RealtorServiceImpl realtorService, MinioService minioService, ObjectInvestorValidator objectInvestorValidator, ObjectBuilderService objectBuilderService) {
        this.propertyInvestorObjectService = propertyInvestorObjectService;
        this.realtorService = realtorService;
        this.minioService = minioService;
        this.objectInvestorValidator = objectInvestorValidator;
        this.objectBuilderService = objectBuilderService;
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public PropertyInvestorObject getById(@PathVariable Integer id) {
        return propertyInvestorObjectService.getById(id);
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
    public ResponseEntity<String> ObjectsInvestorDelete(@PathVariable Integer id) {
        propertyInvestorObjectService.deleteById(id);
        return ResponseEntity.ok().body("The investor object with ID " + id + " has been successfully deleted.");
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("investorObjectsActive", true);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> newObjectsInvestorControllerPost(@Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyInvestorObject propertyInvestorObject = ObjectInvestorMapper.INSTANCE.toEntity(propertyInvestorObjectDTO);
        objectInvestorValidator.validate(propertyInvestorObjectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertyInvestorObjectService.saveCreate(propertyInvestorObject, propertyInvestorObjectDTO);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/filter")
    @ResponseBody
    public Page<PropertyInvestorObject> showPageObjectBuilder(@ModelAttribute InvestorObjectDtoSearch objectDto
            , @RequestParam(name = "page", defaultValue = "0") Integer numberPage, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
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
    public ResponseEntity<?> editObjectsInvestorControllerPost(@PathVariable Integer id, @Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyInvestorObject propertyInvestorObject = propertyInvestorObjectService.findById(id).get();
        objectInvestorValidator.validateEdit(propertyInvestorObjectDTO, bindingResult, propertyInvestorObject.getObjectCode());
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertyInvestorObjectService.saveEdit(propertyInvestorObject, propertyInvestorObjectDTO);
        return ResponseEntity.ok().body("ok");
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFiles(@PathVariable Integer id) {
        Optional<PropertyInvestorObject> objectBuilder = propertyInvestorObjectService.findById(id);
        return minioService.getResponseEntity(objectBuilder.isEmpty(), objectBuilder.get().getFiles(), filesBucketName);
    }


    @GetMapping("/card/{id}")
    public ModelAndView CardObjectsBuilderShow(@PathVariable Integer id) {
        Optional<PropertyInvestorObject> entity = propertyInvestorObjectService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        if (entity.isEmpty()) {
            modelAndView.setViewName("/error/404");
        } else {
            modelAndView.setViewName("/objects/object_investor/cardObjectInvestor");
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


    @GetMapping("/for/select")
    @ResponseBody
    public Page<PropertyInvestorObject> search(@RequestParam("query") String name,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        Page<PropertyInvestorObject> searchResults = propertyInvestorObjectService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }

}
