package org.example.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseForFilterDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.HousesServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    @GetMapping("/edit/{id}")
    public ModelAndView edit(){
        return new ModelAndView("objects/house/house_add");
    }
    @PostMapping("/add/info")
    public ResponseEntity<String> addInfo(@ModelAttribute("materialDTO") HouseMaterialDto materialDTO,
                                          @ModelAttribute("infoDTO") HouseInfoDto infoDTO,
                                          @ModelAttribute("addressDTO") HouseAddressDto addressDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    housesService.add(materialDTO, infoDTO, addressDTO);
    return ResponseEntity.ok().body("Об'єкт збережено успішно");
    }

    @PostMapping("/get/all")
    @ResponseBody
    public Page<PropertyHouseObject> getAll(@ModelAttribute HouseForFilterDto houseForFilterDto){
        return housesService.getAll(houseForFilterDto);
    }
}
