package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BuyerPersonalDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("buyer/buyer_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("buyer/buyer_add");
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@ModelAttribute BuyerPersonalDataDto buyerPersonalDataDto){
        System.out.println(buyerPersonalDataDto.getBirthdate());
        System.out.println(buyerPersonalDataDto.getLastVisit());
        return ResponseEntity.ok().body("Покупця успішно збережено");
    }
}
