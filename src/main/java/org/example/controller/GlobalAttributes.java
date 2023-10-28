package org.example.controller;

import org.example.entity.property.type.*;
import org.example.repository.CityRepository;
import org.example.repository.RegionRepository;
import org.example.repository.TopozoneRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    @ModelAttribute("balconyType")
    public BalconyType[] getPropertyBalconyType() {
        return BalconyType.values();
    }

    @ModelAttribute("bathroomType")
    public BathroomType[] getPropertyBathroomType() {
        return BathroomType.values();
    }

    @ModelAttribute("windowViewType")
    public WindowViewType[] getPropertyWindowViewType() {
        return WindowViewType.values();
    }
    @ModelAttribute("kitchenType")
    public KitchenType[] getPropertyKitchenType() {
        return KitchenType.values();
    }
    @ModelAttribute("kitchenStoveType")
    public KitchenStoveType[] getPropertyKitchenStoveType() {
        return KitchenStoveType.values();
    }
    @ModelAttribute("heatingType")
    public HeatingType[] getPropertyHeatingType() {
        return HeatingType.values();
    }
    @ModelAttribute("ownershipType")
    public OwnershipType[] getPropertyOwnershipType() {
        return OwnershipType.values();
    }
    @ModelAttribute("propertyBuildStatus")
    public PropertyBuildStatus[] getPropertyPropertyBuildStatus() {
        return PropertyBuildStatus.values();
    }
    @ModelAttribute("publicationStatus")
    public PublicationStatus[] getPropertyPublicationStatus() {
        return PublicationStatus.values();
    }

    @ModelAttribute("propertyOrigin")
    public PropertyOrigin[] getPropertyPropertyOrigin() {
        return PropertyOrigin.values();
    }

}
