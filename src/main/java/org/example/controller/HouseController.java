package org.example.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseForFilterDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.HousesServiceImpl;
import org.example.service.MinioService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {
    private final HousesServiceImpl housesService;
    private final MinioService minioService;
    @GetMapping
    public ModelAndView getAll(){
        return new ModelAndView("objects/house/house_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("objects/house/house_add");
    }
    @GetMapping("/{id}")
    public ModelAndView info(){
        return new ModelAndView("objects/house/house_info");
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
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id){
        housesService.deleteById(id);
        return ResponseEntity.ok().body("Елемент видалено успішно");
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public PropertyHouseObject getById(@PathVariable Integer id){
        return housesService.getById(id);
    }
    @GetMapping("/getUrl/{id}")
    public ResponseEntity<String> getUrl(@PathVariable Integer id){
        return ResponseEntity.ok().body(minioService.getUrl(housesService.getById(id).getPictures().get(0)));
    }
    class FilesDto{
        private String originUrl;
        private String url;

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
    public List<FilesDto> getFiles(@PathVariable Integer id){
        PropertyHouseObject house = housesService.getById(id);
        List<FilesDto> filesDtos = new ArrayList<>();
        for (String file : house.getPictures()) {
            FilesDto filesDto = new FilesDto();
            filesDto.setUrl(minioService.getUrl(file));
            filesDto.setOriginUrl(file);
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
        return ResponseEntity.ok().body("Фото з дропзони видалено успішно");
    }
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Integer id, @RequestParam String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject house = housesService.getById(id);
        house.getFiles().remove(url);
        minioService.deleteImg(url, "images");
        housesService.save(house);
        return ResponseEntity.ok().body("Файл видалено успішно");
    }
}
