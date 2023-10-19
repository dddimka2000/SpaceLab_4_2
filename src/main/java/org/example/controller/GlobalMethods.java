package org.example.controller;


import org.example.repository.CityRepository;
import org.example.repository.DistrictRepository;
import org.example.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    public GlobalMethods(CityRepository cityRepository, DistrictRepository districtRepository, StreetRepository streetRepository) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.streetRepository = streetRepository;
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
    @GetMapping("/getStreet/{name}")
    public List<String> getStreet(@PathVariable String name) {
        List<String> list = streetRepository.findByDistrictNameDistrict(name).stream().map(s->s.getName()).collect(Collectors.toList());
        return list;
    }

}
