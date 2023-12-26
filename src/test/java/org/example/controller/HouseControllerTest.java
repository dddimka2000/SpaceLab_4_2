package org.example.controller;

import io.minio.errors.*;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.HousesServiceImpl;
import org.example.service.MinioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseControllerTest {
    @Mock
    private HousesServiceImpl housesService;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private HouseController houseController;
    @Test
    void getAll() {
        ModelAndView modelAndView = houseController.getAll();
        assertEquals("objects/house/house_table", modelAndView.getViewName());
    }

    @Test
    void add() {
        ModelAndView modelAndView = houseController.add();
        assertEquals("objects/house/house_add", modelAndView.getViewName());
    }

    @Test
    void info() {
        ModelAndView modelAndView = houseController.info();
        assertEquals("objects/house/house_info", modelAndView.getViewName());
    }

    @Test
    void edit() {
        ModelAndView modelAndView = houseController.edit();
        assertEquals("objects/house/house_add", modelAndView.getViewName());
    }

    @Test
    void addInfo() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        HouseMaterialDto materialDTO = new HouseMaterialDto();
        HouseInfoDto infoDTO = new HouseInfoDto();
        HouseAddressDto addressDTO = new HouseAddressDto();
        BindingResult bindingResult = new BeanPropertyBindingResult(materialDTO, "materialDTO");
        ResponseEntity<String> responseEntity = houseController.addInfo(materialDTO, bindingResult, infoDTO, bindingResult, addressDTO, bindingResult);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Об'єкт збережено успішно", responseEntity.getBody());
        verify(housesService, times(1)).add(materialDTO, infoDTO, addressDTO);
    }

    @Test
    void testGetAll() {
        ObjectForFilterDto filterDto = new ObjectForFilterDto();
        Page<PropertyHouseObject> page = mock(Page.class);
        when(housesService.getAll(filterDto)).thenReturn(page);
        Page<PropertyHouseObject> result = houseController.getAll(filterDto);
        assertEquals(page, result);
        verify(housesService, times(1)).getAll(filterDto);
    }

    @Test
    void deleteById() {
        Integer objectId = 1;
        ResponseEntity<String> responseEntity = houseController.deleteById(objectId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Елемент видалено успішно", responseEntity.getBody());
        verify(housesService, times(1)).deleteById(objectId);
    }

    @Test
    void getById() {
        Integer objectId = 1;
        PropertyHouseObject object = new PropertyHouseObject();
        when(housesService.getById(objectId)).thenReturn(object);
        PropertyHouseObject result = houseController.getById(objectId);
        assertEquals(object, result);
        verify(housesService, times(1)).getById(objectId);
    }

    @Test
    void getUrl() {
        Integer objectId = 1;
        PropertyHouseObject object = new PropertyHouseObject();
        object.setPictures(Collections.singletonList("test-url"));
        when(housesService.getById(objectId)).thenReturn(object);
        when(minioService.getUrl("test-url")).thenReturn("http://example.com/test-url");
        ResponseEntity<String> responseEntity = houseController.getUrl(objectId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("http://example.com/test-url", responseEntity.getBody());
        verify(housesService, times(1)).getById(objectId);
        verify(minioService, times(1)).getUrl("test-url");
    }

    @Test
    void getFiles() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        PropertyHouseObject houseObject = new PropertyHouseObject();
        houseObject.setPictures(Collections.singletonList("test-url"));
        when(housesService.getById(objectId)).thenReturn(houseObject);
        when(minioService.getUrl("test-url")).thenReturn("http://example.com/test-url");
        when(minioService.getObjectSize("test-url")).thenReturn(1024L);
        List<HouseController.FilesDto> result = houseController.getFiles(objectId);
        assertEquals(1, result.size());
        assertEquals("http://example.com/test-url", result.get(0).getUrl());
        assertEquals("test-url", result.get(0).getOriginUrl());
        assertEquals("1024", result.get(0).getSize());
        verify(housesService, times(1)).getById(objectId);
        verify(minioService, times(1)).getUrl("test-url");
        verify(minioService, times(1)).getObjectSize("test-url");
    }

    @Test
    void deletePicture() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        String url = "test-url";
        PropertyHouseObject houseObject = mock(PropertyHouseObject.class);
        when(housesService.getById(objectId)).thenReturn(houseObject);
        ResponseEntity<String> responseEntity = houseController.deletePicture(objectId, url);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Фото з дропзони видалено успішно", responseEntity.getBody());
        verify(housesService, times(1)).getById(objectId);
        verify(housesService, times(1)).save(houseObject);
        verify(minioService, times(1)).deleteImg(url, "images");
    }

    @Test
    void deleteFile() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        String url = "test-url";
        PropertyHouseObject houseObject = mock(PropertyHouseObject.class);
        when(housesService.getById(objectId)).thenReturn(houseObject);
        ResponseEntity<String> responseEntity = houseController.deleteFile(objectId, url);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Файл видалено успішно", responseEntity.getBody());
        verify(housesService, times(1)).getById(objectId);
        verify(housesService, times(1)).save(houseObject);
        verify(minioService, times(1)).deleteImg(url, "images");
    }

    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        houseController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("housePropertyActive", true);
    }
}