package org.example.controller;

import org.example.entity.*;
import org.example.entity.property.type.*;
import org.example.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnumControllerTest {
    @Mock
    private TopozoneRepository topozoneRepository;
    @Mock
    private DistrictRepository districtRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private StreetEntityRepository streetEntityRepository;
    @Mock
    private RegionRepository regionRepository;
    @InjectMocks
    private EnumController enumController;
    @Test
    void getRoles() {
        List<UserRole> result = enumController.getRoles();
        assertEquals(UserRole.values().length, result.size());
    }

    @Test
    void getInformationSource() {
        List<InformationSource> result = enumController.getInformationSource();
        assertEquals(InformationSource.values().length, result.size());
    }

    @Test
    void getRegion() {
        RegionEntity region = new RegionEntity();
        region.setId(1);
        region.setNameRegion("qwer");
        when(regionRepository.findAll()).thenReturn(List.of(region, new RegionEntity(), new RegionEntity(), new RegionEntity(), new RegionEntity()));
        List<EnumController.ForSelect> result = enumController.getRegion();
        assertEquals("qwer", result.get(0).getId());
        assertEquals( "qwer", result.get(0).getName());
        assertEquals(5, result.size());
    }

    @Test
    void getTopzone() {
        when(topozoneRepository.findAll()).thenReturn(List.of(new TopozoneEntity(), new TopozoneEntity(), new TopozoneEntity()));
        List<EnumController.ForSelect> result = enumController.getTopzone();
        assertEquals(3, result.size());
    }

    @Test
    void getDistrict() {
        when(districtRepository.findAll()).thenReturn(List.of(new DistrictEntity(), new DistrictEntity()));
        List<EnumController.ForSelect> result = enumController.getDistrict();
        assertEquals(2, result.size());
    }

    @Test
    void getStreet() {
        when(streetEntityRepository.findAll()).thenReturn(List.of(new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity(), new StreetEntity()));
        List<EnumController.ForSelect> result = enumController.getStreet();
        assertEquals(10, result.size());
    }

    @Test
    void getCity() {
        when(cityRepository.findAll()).thenReturn(List.of(new CityEntity(), new CityEntity(), new CityEntity(), new CityEntity(), new CityEntity(), new CityEntity(), new CityEntity()));
        List<EnumController.ForSelect> result = enumController.getCity();
        assertEquals(7, result.size());
    }

    @Test
    void getApplicationStatus() {
        List<ApplicationStatus> result = enumController.getApplicationStatus();
        assertEquals(ApplicationStatus.values().length, result.size());
    }

    @Test
    void getOwnership() {
        List<OwnershipType> result = enumController.getOwnership();
        assertEquals(OwnershipType.values().length, result.size());
    }

    @Test
    void getPublicationStatus() {
        List<PublicationStatus> result = enumController.getPublicationStatus();
        assertEquals(PublicationStatus.values().length, result.size());
    }

    @Test
    void getPropertyBuildStatus() {
        List<PropertyBuildStatus> result = enumController.getPropertyBuildStatus();
        assertEquals(PropertyBuildStatus.values().length, result.size());
    }

    @Test
    void getPropertyHouseType() {
        List<PropertyHouseType> result = enumController.getPropertyHouseType();
        assertEquals(PropertyHouseType.values().length, result.size());
    }

    @Test
    void getPlotPurposeType() {
        List<PlotPurposeType> result = enumController.getPlotPurposeType();
        assertEquals(PlotPurposeType.values().length, result.size());
    }

    @Test
    void getPropertyOrigin() {
        List<PropertyOrigin> result = enumController.getPropertyOrigin();
        assertEquals(PropertyOrigin.values().length, result.size());
    }

    @Test
    void getPropertyApplicationType() {
        List<PropertyApplicationType> result = enumController.getPropertyApplicationType();
        assertEquals(PropertyApplicationType.values().length, result.size());
    }

    @Test
    void getHouseState() {
        List<HouseState> result = enumController.getHouseState();
        assertEquals(HouseState.values().length, result.size());
    }

    @Test
    void getKitchenTypes() {
        List<KitchenType> result = enumController.getKitchenTypes();
        assertEquals(KitchenType.values().length, result.size());
    }

    @Test
    void getBathroomTypes() {
        List<BathroomType> result = enumController.getBathroomTypes();
        assertEquals(BathroomType.values().length, result.size());
    }

    @Test
    void getGasTypes() {
        List<GasType> result = enumController.getGasTypes();
        assertEquals(GasType.values().length, result.size());
    }

    @Test
    void getWaterTypes() {
        List<WaterType> result = enumController.getWaterTypes();
        assertEquals(WaterType.values().length, result.size());
    }

    @Test
    void getSewerageTypes() {
        List<SewageType> result = enumController.getSewerageTypes();
        assertEquals(SewageType.values().length, result.size());
    }

    @Test
    void getHeatingTypes() {
        List<HeatingType> result = enumController.getHeatingTypes();
        assertEquals(HeatingType.values().length, result.size());
    }

    @Test
    void getLadderTypes() {
        List<LadderType> result = enumController.getLadderTypes();
        assertEquals(LadderType.values().length, result.size());
    }

    @Test
    void getRoofTypes() {
        List<RoofType> result = enumController.getRoofTypes();
        assertEquals(RoofType.values().length, result.size());
    }

    @Test
    void getFloorTypes() {
        List<FloorType> result = enumController.getFloorTypes();
        assertEquals(FloorType.values().length, result.size());
    }

    @Test
    void getWindowTypes() {
        List<WindowType> result = enumController.getWindowTypes();
        assertEquals(WindowType.values().length, result.size());
    }

    @Test
    void getCarpentryType() {
        List<CarpentryType> result = enumController.getCarpentryType();
        assertEquals(CarpentryType.values().length, result.size());
    }

    @Test
    void getFrontDoorType() {
        List<FrontDoorType> result = enumController.getFrontDoorType();
        assertEquals(FrontDoorType.values().length, result.size());
    }

    @Test
    void getAirConditioning() {
        List<AirConditioningType> result = enumController.getAirConditioning();
        assertEquals(AirConditioningType.values().length, result.size());
    }

    @Test
    void getVentilation() {
        List<VentilationType> result = enumController.getVentilation();
        assertEquals(VentilationType.values().length, result.size());
    }

    @Test
    void getElectricity() {
        List<ElectricityType> result = enumController.getElectricity();
        assertEquals(ElectricityType.values().length, result.size());
    }

    @Test
    void getWindowViewType() {
        List<WindowViewType> result = enumController.getWindowViewType();
        assertEquals(WindowViewType.values().length, result.size());
    }

    @Test
    void getPropertyStructureState() {
        List<PropertyStructureState> result = enumController.getPropertyStructureState();
        assertEquals(PropertyStructureState.values().length, result.size());
    }

    @Test
    void getBuildStatus() {
        List<PropertyBuildStatus> result = enumController.getBuildStatus();
        assertEquals(PropertyBuildStatus.values().length, result.size());
    }

    @Test
    void getPropertyCommercialType() {
        List<PropertyCommercialType> result = enumController.getPropertyCommercialType();
        assertEquals(PropertyCommercialType.values().length, result.size());
    }
}