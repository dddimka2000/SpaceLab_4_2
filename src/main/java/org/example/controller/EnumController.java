package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.property.type.*;
import org.example.repository.*;
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
    private final CityRepository cityRepository;
    private final StreetEntityRepository streetEntityRepository;
    private final RegionRepository regionRepository;
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
    @GetMapping("/region")
    @ResponseBody
    public List<ForSelect> getRegion(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (RegionEntity region : regionRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(region.getNameRegion());
            forSelect.setName(region.getNameRegion());
            forSelects.add(forSelect);
        }
        return forSelects;
    }
    @GetMapping("/topzone")
    @ResponseBody
    public List<ForSelect> getTopzone(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (TopozoneEntity topozoneEntity : topozoneRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(topozoneEntity.getName());
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
            forSelect.setId(districtEntity.getNameDistrict());
            forSelect.setName(districtEntity.getNameDistrict());
            forSelects.add(forSelect);
        }
        return forSelects;
    }
    @GetMapping("/street")
    @ResponseBody
    public List<ForSelect> getStreet(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (StreetEntity street : streetEntityRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(street.getName());
            forSelect.setName(street.getName());
            forSelects.add(forSelect);
        }
        return forSelects;
    }
    @GetMapping("/city")
    @ResponseBody
    public List<ForSelect> getCity(){
        List<ForSelect> forSelects = new ArrayList<>();
        for (CityEntity city : cityRepository.findAll()) {
            ForSelect forSelect = new ForSelect();
            forSelect.setId(city.getNameCity());
            forSelect.setName(city.getNameCity());
            forSelects.add(forSelect);
        }
        return forSelects;
    }

    @GetMapping("/applicationStatus")
    @ResponseBody
    public List<ApplicationStatus> getApplicationStatus(){
        return List.of(ApplicationStatus.values());
    }
    @GetMapping("/ownership")
    @ResponseBody
    public List<OwnershipType> getOwnership(){
        return List.of(OwnershipType.values());
    }
    @GetMapping("/publicationStatus")
    @ResponseBody
    public List<PublicationStatus> getPublicationStatus(){
        return List.of(PublicationStatus.values());
    }
    @GetMapping("/propertyBuildStatus")
    @ResponseBody
    public List<PropertyBuildStatus> getPropertyBuildStatus(){
        return List.of(PropertyBuildStatus.values());
    }
    @GetMapping("/propertyType")
    @ResponseBody
    public List<PropertyHouseType> getPropertyHouseType(){
        return List.of(PropertyHouseType.values());
    }
    @GetMapping("/plotPurposeType")
    @ResponseBody
    public List<PlotPurposeType> getPlotPurposeType(){
        return List.of(PlotPurposeType.values());
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



    @GetMapping("/stateHouse")
    @ResponseBody
    public List<HouseState> getHouseState() {
        return List.of(HouseState.values());
    }

    @GetMapping("/kitchen")
    @ResponseBody
    public List<KitchenType> getKitchenTypes() {
        return List.of(KitchenType.values());
    }

    @GetMapping("/bathroom")
    @ResponseBody
    public List<BathroomType> getBathroomTypes() {
        return List.of(BathroomType.values());
    }

    @GetMapping("/gas")
    @ResponseBody
    public List<GasType> getGasTypes() {
        return List.of(GasType.values());
    }

    @GetMapping("/water")
    @ResponseBody
    public List<WaterType> getWaterTypes() {
        return List.of(WaterType.values());
    }

    @GetMapping("/sewerage")
    @ResponseBody
    public List<SewageType> getSewerageTypes() {
        return List.of(SewageType.values());
    }

    @GetMapping("/heating")
    @ResponseBody
    public List<HeatingType> getHeatingTypes() {
        return List.of(HeatingType.values());
    }

    @GetMapping("/ladder")
    @ResponseBody
    public List<LadderType> getLadderTypes() {
        return List.of(LadderType.values());
    }

    @GetMapping("/roof")
    @ResponseBody
    public List<RoofType> getRoofTypes() {
        return List.of(RoofType.values());
    }

    @GetMapping("/floor")
    @ResponseBody
    public List<FloorType> getFloorTypes() {
        return List.of(FloorType.values());
    }

    @GetMapping("/window")
    @ResponseBody
    public List<WindowType> getWindowTypes() {
        return List.of(WindowType.values());
    }
    @GetMapping("/carpentryType")
    @ResponseBody
    public List<CarpentryType> getCarpentryType() {
        return List.of(CarpentryType.values());
    }
    @GetMapping("/frontDoorType")
    @ResponseBody
    public List<FrontDoorType> getFrontDoorType() {
        return List.of(FrontDoorType.values());
    }
}