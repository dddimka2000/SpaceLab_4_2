package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.DistrictEntity;
import org.example.entity.TopozoneEntity;
import org.example.entity.UserRole;
import org.example.entity.property.type.ApplicationStatus;
import org.example.entity.property.type.InformationSource;
import org.example.entity.property.type.PropertyApplicationType;
import org.example.entity.property.type.PropertyOrigin;
import org.example.repository.DistrictRepository;
import org.example.repository.TopozoneRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {
    private final TopozoneRepository topozoneRepository;
    private final DistrictRepository districtRepository;
    public static class ForSelect {
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    @GetMapping("/role")
    @ResponseBody
    public List<UserRole> getRoles(){
        return List.of(UserRole.values());
    }
    @GetMapping("/informationSource")
    @ResponseBody
    public List<InformationSource> getInformationSource(){
        return List.of(InformationSource.values());
    }
    @GetMapping("/topzone")
    @ResponseBody
    public List<ForSelect> getTopzone(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (TopozoneEntity topozoneEntity : topozoneRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(topozoneEntity.getId().toString());
            forSelect.setName(topozoneEntity.getName());
            forSelects.add(forSelect);
        }
        return forSelects;
    }
    @GetMapping("/district")
    @ResponseBody
    public List<ForSelect> getDistrict(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (DistrictEntity districtEntity : districtRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(districtEntity.getId().toString());
            forSelect.setName(districtEntity.getNameDistrict());
            forSelects.add(forSelect);
        }
        return forSelects;
    }
    @GetMapping("/applicationStatus")
    @ResponseBody
    public List<ApplicationStatus> getApplicationStatus(){
        return List.of(ApplicationStatus.values());
    }
    @GetMapping("/propertyOrigin")
    @ResponseBody
    public List<PropertyOrigin> getPropertyOrigin(){
        return List.of(PropertyOrigin.values());
    }
    @GetMapping("/propertyApplicationType")
    @ResponseBody
    public List<PropertyApplicationType> getPropertyApplicationType(){
        return List.of(PropertyApplicationType.values());
    }
}