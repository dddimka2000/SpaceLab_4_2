package org.example.controller;

import org.example.entity.RegionEntity;
import org.example.entity.TopozoneEntity;
import org.example.entity.UserEntity;
import org.example.entity.property.type.*;
import org.example.repository.*;
import org.example.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalAttributesTest {
    @Mock
    private TopozoneRepository topozoneRepository;
    @Mock
    private RegionRepository regionRepository;
    @InjectMocks
    private GlobalAttributes globalAttributes;
    @Test
    void getValueNameUser() {
        UserDetailsImpl userDetails = new UserDetailsImpl(mock(UserEntity.class));

        Optional<UserDetailsImpl> result = globalAttributes.getValueNameUser(userDetails);

        assertEquals(userDetails, result.get());
    }
    @Test
    void getValueNameUserWhereUserIsNull() {
        Optional<UserDetailsImpl> result = globalAttributes.getValueNameUser(null);
        assertFalse(result.isPresent());
    }
    @Test
    void getCarpentryType() {
        CarpentryType[] result = globalAttributes.getCarpentryType();
        assertEquals(CarpentryType.values().length, result.length);
    }

    @Test
    void getLadderType() {
        LadderType[] result = globalAttributes.getLadderType();
        assertEquals(LadderType.values().length, result.length);
    }

    @Test
    void getFrontDoorType() {
        FrontDoorType[] result = globalAttributes.getFrontDoorType();
        assertEquals(FrontDoorType.values().length, result.length);
    }

    @Test
    void getFloorType() {
        FloorType[] result = globalAttributes.getFloorType();
        assertEquals(FloorType.values().length, result.length);
    }

    @Test
    void getWindowType() {
        WindowType[] result = globalAttributes.getWindowType();
        assertEquals(WindowType.values().length, result.length);
    }

    @Test
    void getHouseProjectType() {
        HouseProjectType[] result = globalAttributes.getHouseProjectType();
        assertEquals(HouseProjectType.values().length, result.length);
    }

    @Test
    void getLayoutType() {
        LayoutType[] result = globalAttributes.getLayoutType();
        assertEquals(LayoutType.values().length, result.length);
    }

    @Test
    void getPropertySecondaryType() {
        PropertySecondaryType[] result = globalAttributes.getPropertySecondaryType();
        assertEquals(PropertySecondaryType.values().length, result.length);
    }

    @Test
    void getPropertyBuildStatus() {
        PropertyBuildStatus[] result = globalAttributes.getPropertyBuildStatus();
        assertEquals(PropertyBuildStatus.values().length, result.length);
    }

    @Test
    void getRegions() {
        when(regionRepository.findAll()).thenReturn(List.of(new RegionEntity(), new RegionEntity(), new RegionEntity()));
        List<String> result = globalAttributes.getRegions();
        assertEquals(3, result.size());
    }

    @Test
    void getTopozone() {
        when(topozoneRepository.findAll()).thenReturn(List.of(new TopozoneEntity(), new TopozoneEntity(), new TopozoneEntity(), new TopozoneEntity(), new TopozoneEntity(), new TopozoneEntity()));
        List<String> result = globalAttributes.getTopozone();
        assertEquals(6, result.size());
    }

    @Test
    void getPropertyBalconyType() {
        BalconyType[] result = globalAttributes.getPropertyBalconyType();
        assertEquals(BalconyType.values().length, result.length);
    }

    @Test
    void getPropertyBathroomType() {
        BathroomType[] result = globalAttributes.getPropertyBathroomType();
        assertEquals(BathroomType.values().length, result.length);
    }

    @Test
    void getPropertyWindowViewType() {
        WindowViewType[] result = globalAttributes.getPropertyWindowViewType();
        assertEquals(WindowViewType.values().length, result.length);
    }

    @Test
    void getPropertyKitchenType() {
        KitchenType[] result = globalAttributes.getPropertyKitchenType();
        assertEquals(KitchenType.values().length, result.length);
    }

    @Test
    void getPropertyKitchenStoveType() {
        KitchenStoveType[] result = globalAttributes.getPropertyKitchenStoveType();
        assertEquals(KitchenStoveType.values().length, result.length);
    }

    @Test
    void getPropertyHeatingType() {
        HeatingType[] result = globalAttributes.getPropertyHeatingType();
        assertEquals(HeatingType.values().length, result.length);
    }

    @Test
    void getPropertyOwnershipType() {
        OwnershipType[] result = globalAttributes.getPropertyOwnershipType();
        assertEquals(OwnershipType.values().length, result.length);
    }

    @Test
    void getPropertyPropertyBuildStatus() {
        PropertyBuildStatus[] result = globalAttributes.getPropertyPropertyBuildStatus();
        assertEquals(PropertyBuildStatus.values().length, result.length);
    }

    @Test
    void getPropertyPublicationStatus() {
        PublicationStatus[] result = globalAttributes.getPropertyPublicationStatus();
        assertEquals(PublicationStatus.values().length, result.length);
    }

    @Test
    void getPropertyPropertyOrigin() {
        PropertyOrigin[] result = globalAttributes.getPropertyPropertyOrigin();
        assertEquals(PropertyOrigin.values().length, result.length);
    }
}