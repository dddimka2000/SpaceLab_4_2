package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.InvestorObjectDtoSearch;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.BuilderObject;
import org.example.entity.Realtor;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.mapper.ObjectSecondaryMapper;
import org.example.service.MinioService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
