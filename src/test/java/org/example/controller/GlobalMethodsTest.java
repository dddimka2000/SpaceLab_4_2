package org.example.controller;

import org.example.entity.BuilderObject;
import org.example.entity.CityEntity;
import org.example.entity.DistrictEntity;
import org.example.entity.StreetEntity;
import org.example.repository.CityRepository;
import org.example.repository.DistrictRepository;
import org.example.repository.StreetRepository;
import org.example.service.ExelService;
import org.example.service.ObjectBuilderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalMethodsTest {

    @InjectMocks
    private GlobalMethods globalMethods;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private StreetRepository streetRepository;

    @Mock
    private ObjectBuilderService objectBuilderService;

    @Mock
    private ExelService exelService;

    @Test
    void testGetCity() {
        List<CityEntity> cities = Arrays.asList(new CityEntity(), new CityEntity());
        when(cityRepository.findByRegionNameRegion(anyString())).thenReturn(cities);

        List<String> result = globalMethods.getCity("afd");

        assertEquals(2, result.size());
    }

    @Test
    void testGetDistrict() {
        List<DistrictEntity> districts = Arrays.asList(new DistrictEntity(), new DistrictEntity());
        when(districtRepository.findByCityNameCity(anyString())).thenReturn(districts);

        List<String> result = globalMethods.getDistrict("cityName");

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllDistricts() {
        List<DistrictEntity> districts = Arrays.asList(new DistrictEntity(), new DistrictEntity());
        when(districtRepository.findAll()).thenReturn(districts);

        List<String> result = globalMethods.getDistrict();

        assertEquals(2, result.size());
    }

    @Test
    void testGetResidentialComplex() {
        when(objectBuilderService.findBuilderObjectsPage(0, 10)).thenReturn(mock(Page.class));

        List<String> result = globalMethods.getResidentialComplex("name", null, 0);

        assertEquals(0, result.size());
    }

    @Test
    void testGetStreet() {
        List<StreetEntity> streets = Arrays.asList(new StreetEntity(), new StreetEntity());
        when(streetRepository.findByDistrictNameDistrict(anyString())).thenReturn(streets);

        List<String> result = globalMethods.getStreet("districtName");

        assertEquals(2, result.size());
    }

    @Test
    void testCheckStreetExist() {
        String streetName = "Street1";
        when(exelService.checkStreetExist(streetName)).thenReturn(true);

        ResponseEntity responseEntity = globalMethods.checkStreet(streetName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Улица(укр) найдена.", responseEntity.getBody());
    }

    @Test
    void testCheckStreetNotExist() {
        String streetName = "Street2";
        when(exelService.checkStreetExist(streetName)).thenReturn(false);

        ResponseEntity responseEntity = globalMethods.checkStreet(streetName);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Указанная вами улица (украинское название) не была обнаружена. Пожалуйста, удостоверьтесь, что вы правильно ввели название улицы. Возможно, добавление приставки 'вул.' перед названием улицы поможет. Например, 'вул. Горького'.", responseEntity.getBody());
    }
}
