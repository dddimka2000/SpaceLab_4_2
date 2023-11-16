package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.CommercialAddressDto;
import org.example.dto.CommercialInfoDto;
import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.service.CommercialServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/commercial")
@RequiredArgsConstructor
@Log4j2
public class CommercialObjectsController {
    private final CommercialServiceImpl commercialService;
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("objects/commercial/commercial_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("objects/commercial/commercial_add");
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @ModelAttribute CommercialInfoDto commercialInfoDto, BindingResult bindingResult1,
                                      @Valid @ModelAttribute CommercialMaterialAndAreaDto commercialMaterialAndAreaDto, BindingResult bindingResult2,
                                      @Valid @ModelAttribute CommercialAddressDto commercialAddressDto, BindingResult bindingResult3) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if(bindingResult1.hasErrors())return ResponseEntity.ok().body("ERROR: "+bindingResult1.getAllErrors().get(0).getDefaultMessage());
        if(bindingResult2.hasErrors())return ResponseEntity.ok().body("ERROR: "+bindingResult2.getAllErrors().get(0).getDefaultMessage());
        if(bindingResult3.hasErrors())return ResponseEntity.ok().body("ERROR: "+bindingResult3.getAllErrors().get(0).getDefaultMessage());
        commercialService.add(commercialInfoDto, commercialMaterialAndAreaDto, commercialAddressDto);
        return ResponseEntity.ok().body("Об'єкт збережено успішно");
    }
}
