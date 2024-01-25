package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.ObjectForFilterDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.HousesServiceImpl;
import org.example.service.MinioService;
import org.example.util.validator.HouseValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HousesServiceImpl housesService;
    private final MinioService minioService;
    private final HouseValidator houseValidator;

    @GetMapping
    public ModelAndView getAll() {
        return new ModelAndView("objects/house/house_table");
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("objects/house/house_add");
    }

    @GetMapping("/{id}")
    public ModelAndView info() {
        return new ModelAndView("objects/house/house_info");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit() {
        return new ModelAndView("objects/house/house_add");
    }

    @PostMapping("/add/info")
    public ResponseEntity<Map<String, String>> addInfo(
            @ModelAttribute("materialDTO") @Valid HouseMaterialDto materialDTO, BindingResult bindingResult1,
            @ModelAttribute("infoDTO") @Valid HouseInfoDto infoDTO, BindingResult bindingResult2,
            @ModelAttribute("addressDTO") @Valid HouseAddressDto addressDTO, BindingResult bindingResult3
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        Map<String, String> errorsMap = new HashMap<>();
        houseValidator.validate(infoDTO, bindingResult2);
        if (bindingResult1.hasErrors()) {
            bindingResult1.getFieldErrors().forEach(error -> errorsMap.put(error.getField(), error.getDefaultMessage()));
        }
        if (bindingResult2.hasErrors()) {
            bindingResult2.getFieldErrors().forEach(error -> errorsMap.put(error.getField(), error.getDefaultMessage()));
        }
        if (bindingResult3.hasErrors()) {
            bindingResult3.getFieldErrors().forEach(error -> errorsMap.put(error.getField(), error.getDefaultMessage()));
        }
        if (!errorsMap.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsMap);
        }
        housesService.add(materialDTO, infoDTO, addressDTO);
        return ResponseEntity.ok().body(Collections.singletonMap("status", "saveObj"));
    }


    @GetMapping("/get/all")
    @ResponseBody
    public Page<PropertyHouseObject> getAll(@ModelAttribute ObjectForFilterDto objectForFilterDto) {
        return housesService.getAll(objectForFilterDto);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        housesService.deleteById(id);
        return ResponseEntity.ok().body("deleteObj");
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public PropertyHouseObject getById(@PathVariable Integer id) {
        return housesService.getById(id);
    }

    @GetMapping("/getUrl/{id}")
    public ResponseEntity<String> getUrl(@PathVariable Integer id) {
        return ResponseEntity.ok().body(minioService.getUrl(housesService.getById(id).getPictures().get(0)));
    }

    class FilesDto {
        private String originUrl;
        private String url;

        public void setSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

        private String size;

        public String getOriginUrl() {
            return originUrl;
        }

        public void setOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @GetMapping("/getPicture/{id}")
    @ResponseBody
    public List<FilesDto> getFiles(@PathVariable Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject house = housesService.getById(id);
        List<FilesDto> filesDtos = new ArrayList<>();
        for (String file : house.getPictures()) {
            FilesDto filesDto = new FilesDto();
            filesDto.setUrl(minioService.getUrl(file));
            filesDto.setOriginUrl(file);
            filesDto.setSize(String.valueOf(minioService.getObjectSize(file)));
            filesDtos.add(filesDto);
        }
        return filesDtos;
    }

    @DeleteMapping("/deletePicture/{id}")
    public ResponseEntity<String> deletePicture(@PathVariable Integer id, @RequestParam String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject house = housesService.getById(id);
        house.getPictures().remove(url);
        minioService.deleteImg(url, "images");
        housesService.save(house);
        return ResponseEntity.ok().body("deleteObj");
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Integer id, @RequestParam String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject house = housesService.getById(id);
        house.getFiles().remove(url);
        minioService.deleteImg(url, "images");
        housesService.save(house);
        return ResponseEntity.ok().body("deleteObj");
    }
    @DeleteMapping("/delete/files")
    public ResponseEntity<String> deleteFile(@RequestParam List<String> urls , @RequestParam int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject commercialObject = housesService.getById(id);
        for (String url : urls) {
            commercialObject.getFiles().remove(url);
            minioService.deleteImg(url, "images");
            housesService.save(commercialObject);

        }
        return ResponseEntity.ok().body("deleteObj");
    }
    @DeleteMapping("/delete/images")
    public ResponseEntity<String> deleteImages(@RequestParam List<String> urls , @RequestParam int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject commercialObject = housesService.getById(id);
        for (String url : urls) {
            commercialObject.getPictures().remove(url);
            minioService.deleteImg(url, "images");
            housesService.save(commercialObject);

        }
        return ResponseEntity.ok().body("deleteObj");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("housePropertyActive", true);
    }
}
