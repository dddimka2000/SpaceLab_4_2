package org.example.controller;


import org.example.repository.CityRepository;
import org.example.repository.DistrictRepository;
import org.example.repository.StreetRepository;
import org.example.service.ExelService;
import org.example.service.ObjectBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GlobalMethods {
    private final
    CityRepository cityRepository;
    private final
    DistrictRepository districtRepository;
    private final
    StreetRepository streetRepository;
    
    public final
    ObjectBuilderService objectBuilderService;
    public final
    ExelService exelService;

    public GlobalMethods(CityRepository cityRepository, DistrictRepository districtRepository, StreetRepository streetRepository, ObjectBuilderService objectBuilderService, ExelService exelService) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.streetRepository = streetRepository;
        this.objectBuilderService = objectBuilderService;
        this.exelService = exelService;
    }

    @GetMapping("/getCity/{name}")
    public List<String> getCity(@PathVariable String name) {
        List<String> list = cityRepository.findByRegionNameRegion(name).stream().map(s->s.getNameCity()).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/getDistrict/{name}")
    public List<String> getDistrict(@PathVariable String name) {
        List<String> list = districtRepository.findByCityNameCity(name).stream().map(s->s.getNameDistrict()).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/getAllDistricts")
    public List<String> getDistrict() {
        List<String> list = districtRepository.findAll().stream().map(s->s.getNameDistrict()).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/getResidentialComplex/{name}")
    public List<String> getResidentialComplex(@PathVariable String name, String searchName,  @RequestParam(name = "page", defaultValue = "0") Integer numberPage) {
        List<String> list = objectBuilderService.findBuilderObjectsPage(numberPage,10).stream().map(s->s.getName()).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/getStreet/{name}")
    public List<String> getStreet(@PathVariable String name) {
        List<String> list = streetRepository.findByDistrictNameDistrict(name).stream().map(s->s.getName()).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/checkStreet")
    public ResponseEntity<?> checkStreet( String name) {
        Boolean checkStreetExist = exelService.checkStreetExist(name);
        if(checkStreetExist){
            return ResponseEntity.ok().body("Улица(укр) найдена.");
        }
        return ResponseEntity
                .badRequest()
                .body("Указанная вами улица (украинское название) не была обнаружена. Пожалуйста, удостоверьтесь, что вы правильно ввели название улицы. Возможно, добавление приставки 'вул.' перед названием улицы поможет. Например, 'вул. Горького'.");
    }
}
