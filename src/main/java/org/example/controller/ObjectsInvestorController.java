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
import org.example.service.ImagesForObjectService;
import org.example.service.MinioService;
import org.example.service.PropertyInvestorObjectService;
import org.example.service.RealtorServiceImpl;
import org.example.service.specification.InvestorObjectSpecification;
import org.example.util.validator.ObjectInvestorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int pageSize=2;


    public ObjectsInvestorController(PropertyInvestorObjectService propertyInvestorObjectService, RealtorServiceImpl realtorService, MinioService minioService, ImagesForObjectService imagesForObjectService, ObjectInvestorValidator objectInvestorValidator) {
        this.propertyInvestorObjectService = propertyInvestorObjectService;
        this.realtorService = realtorService;
        this.minioService = minioService;
        this.imagesForObjectService = imagesForObjectService;
        this.objectInvestorValidator = objectInvestorValidator;
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
    private final
    ObjectInvestorValidator objectInvestorValidator;
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity newObjectsInvestorControllerPost(@Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info(propertyInvestorObjectDTO);
        PropertyInvestorObject propertyInvestorObject = ObjectInvestorMapper.INSTANCE.toEntity(propertyInvestorObjectDTO);
        log.info(propertyInvestorObject);
        Realtor realtor=new Realtor();
        try {
            realtor = realtorService.getById(propertyInvestorObjectDTO.getEmployeeCode());
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("employeeCode","","Кода данного сотрудника не существует");
        }
        objectInvestorValidator.validate(propertyInvestorObjectDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        propertyInvestorObject.setRealtor(realtor);
        List<String> nameFiles=new ArrayList<>();
        for(MultipartFile multipartFile: propertyInvestorObjectDTO.getFiles()){
            nameFiles.add(minioService.putFile(multipartFile));
        }
        propertyInvestorObject.setFiles(nameFiles);
        List<String> namePictures=new ArrayList<>();
        for(MultipartFile multipartFile: propertyInvestorObjectDTO.getPictures()){
            namePictures.add(minioService.putImage(multipartFile));
        }
        propertyInvestorObject.setPictures(namePictures);
        propertyInvestorObjectService.save(propertyInvestorObject);
        return ResponseEntity.ok().body("ok");
    }
    @GetMapping("/filter")
    @ResponseBody
    public Page<PropertyInvestorObject> showPageObjectBuilder(@ModelAttribute InvestorObjectDtoSearch objectDto
            , @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
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
    @GetMapping("/for/select")
    @ResponseBody
    public Page<PropertyInvestorObject> search(@RequestParam("query") String name,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        Page<PropertyInvestorObject> searchResults = propertyInvestorObjectService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }

}
