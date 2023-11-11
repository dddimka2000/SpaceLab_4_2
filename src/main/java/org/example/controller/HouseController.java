package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.service.HousesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HousesServiceImpl housesService;
    @GetMapping
    public ModelAndView getAll(){
        return new ModelAndView("objects/house/house_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("objects/house/house_add");
    }
    @PostMapping("/add/address")
    public ResponseEntity<String> addAddress(@ModelAttribute HouseAddressDto houseAddressDto){
        HouseAddressDto houseAddressDto1 = houseAddressDto;
        return ResponseEntity.ok().body("Об'єкт збережено успішно");
    }
    @PostMapping("/add/info")
    public ResponseEntity<String> addInfo(@ModelAttribute HouseInfoDto houseInfoDto){
        HouseInfoDto houseInfoDto1 = houseInfoDto;
        return ResponseEntity.ok().body("Об'єкт збережено успішно");
    }
    @PostMapping("/add/material")
    public ResponseEntity<String> addMaterial(@ModelAttribute HouseMaterialDto houseMaterialDto){
        HouseMaterialDto houseMaterialDto1 = houseMaterialDto;
        return ResponseEntity.ok().body("Об'єкт збережено успішно");
    }

}
