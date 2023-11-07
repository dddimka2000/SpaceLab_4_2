package org.example.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.BuyerApplication;
import org.example.mapper.BuyerMapper;
import org.example.service.BuyerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
    private final BuyerServiceImpl buyerService;
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("buyer/buyer_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("buyer/buyer_add");
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@ModelAttribute BuyerPersonalDataDto buyerPersonalDataDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        buyerService.addPersonalData(buyerPersonalDataDto);
        return ResponseEntity.ok().body("Покупця успішно збережено");
    }
    @PostMapping("/add/application")
    public ResponseEntity<String> addApplication(@ModelAttribute BuyerApplication buyerApplication){
        buyerService.addApplication(buyerApplication);
        return ResponseEntity.ok().body("Заявку успішно збережено");
    }
}
