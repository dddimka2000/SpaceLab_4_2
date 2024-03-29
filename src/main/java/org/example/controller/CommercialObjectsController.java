package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.CommercialAddressDto;
import org.example.dto.CommercialInfoDto;
import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.CommercialServiceImpl;
import org.example.service.MinioService;
import org.example.util.validator.CommercialValidator;
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
@RequestMapping("/commercial")
@RequiredArgsConstructor
@Log4j2
public class CommercialObjectsController {
    private final CommercialServiceImpl commercialService;
    private final MinioService minioService;
    private final CommercialValidator commercialValidator;
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("objects/commercial/commercial_table");
    }
    @GetMapping("/{id}")
    public ModelAndView info(){
        return new ModelAndView("objects/commercial/commercial_info");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("objects/commercial/commercial_add");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(){
        return new ModelAndView("objects/commercial/commercial_add");
    }
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> add(@Valid @ModelAttribute CommercialInfoDto commercialInfoDto, BindingResult bindingResult1,
                                                   @Valid @ModelAttribute CommercialMaterialAndAreaDto commercialMaterialAndAreaDto, BindingResult bindingResult2,
                                                   @Valid @ModelAttribute CommercialAddressDto commercialAddressDto, BindingResult bindingResult3) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Map<String, String> errorsMap = new HashMap<>();
        commercialValidator.validate(commercialInfoDto, bindingResult1);
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

        commercialService.add(commercialInfoDto, commercialMaterialAndAreaDto, commercialAddressDto);
        return ResponseEntity.ok().body(Collections.singletonMap("status", "saveObj"));
    }
    @GetMapping("/get/all")
    @ResponseBody
    public Page<PropertyCommercialObject> getAll(@ModelAttribute ObjectForFilterDto objectForFilterDto) {
        return commercialService.getAll(objectForFilterDto);
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public PropertyCommercialObject getById(@PathVariable Integer id){
        return commercialService.getById(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        commercialService.deleteById(id);
        return ResponseEntity.ok().body("deleteObj");
    }
    @GetMapping("/getUrl/{id}")
    public ResponseEntity<String> getUrl(@PathVariable Integer id) {
        return ResponseEntity.ok().body(minioService.getUrl(commercialService.getById(id).getPictures().get(0)));
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
        PropertyCommercialObject commercial = commercialService.getById(id);
        List<FilesDto> filesDtos = new ArrayList<>();
        for (String file : commercial.getPictures()) {
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
        PropertyCommercialObject house = commercialService.getById(id);
        house.getPictures().remove(url);
        minioService.deleteImg(url, "images");
        commercialService.save(house);
        return ResponseEntity.ok().body("deleteObj");
    }
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Integer id, @RequestParam String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyCommercialObject house = commercialService.getById(id);
        house.getFiles().remove(url);
        minioService.deleteImg(url, "images");
        commercialService.save(house);
        return ResponseEntity.ok().body("deleteObj");
    }
    @DeleteMapping("/delete/files")
    public ResponseEntity<String> deleteFile(@RequestParam List<String> urls , @RequestParam int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyCommercialObject commercialObject = commercialService.getById(id);
        for (String url : urls) {
            commercialObject.getFiles().remove(url);
            minioService.deleteImg(url, "images");
            commercialService.save(commercialObject);

        }
        return ResponseEntity.ok().body("deleteObj");
    }
    @DeleteMapping("/delete/images")
    public ResponseEntity<String> deleteImages(@RequestParam List<String> urls , @RequestParam int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyCommercialObject commercialObject = commercialService.getById(id);
        for (String url : urls) {
            commercialObject.getPictures().remove(url);
            minioService.deleteImg(url, "images");
            commercialService.save(commercialObject);

        }
        return ResponseEntity.ok().body("deleteObj");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("commercialPropertyActive", true);
    }
}