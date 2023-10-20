package org.example.controller;

import org.example.repository.CityRepository;
import org.example.repository.RegionRepository;
import org.example.repository.TopozoneRepository;
import org.example.security.UserDetailsImpl;
import org.example.util.property.PropertyBuildStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalAttributes {
    private final
    CityRepository cityRepository;
    private final
    RegionRepository regionRepository;
    private final
    TopozoneRepository topozoneRepository;

    public GlobalAttributes(CityRepository cityRepository, RegionRepository regionRepository, TopozoneRepository topozoneRepository) {
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
        this.topozoneRepository = topozoneRepository;
    }

    @ModelAttribute("loggedInUser")
    public Optional<UserDetailsImpl> getValueNameUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }

    @ModelAttribute("propertyBuildStatus")
    public PropertyBuildStatus[] getPropertyBuildStatus() {
        return PropertyBuildStatus.values();
    }

    @ModelAttribute("selectRegion")
    public List<String> getRegions() {
        List<String> list = regionRepository.findAll().stream().map(s -> s.getNameRegion()).collect(Collectors.toList());
        return list;
    }
    @ModelAttribute("selectTopozone")
    public List<String> getTopozone() {
        List<String> list = topozoneRepository.findAll().stream().map(s -> s.getName()).collect(Collectors.toList());
        return list;
    }

}
