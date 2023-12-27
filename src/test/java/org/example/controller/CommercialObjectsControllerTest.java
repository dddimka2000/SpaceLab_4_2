package org.example.controller;

import io.minio.errors.*;
import org.example.dto.CommercialAddressDto;
import org.example.dto.CommercialInfoDto;
import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.service.CommercialServiceImpl;
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
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommercialObjectsControllerTest {
    @Mock
    private CommercialServiceImpl commercialService;
    @Mock
    private MinioService minioService;
    @InjectMocks
    private CommercialObjectsController commercialObjectsController;
    @Test
    void index() {
        ModelAndView modelAndView = commercialObjectsController.index();
        assertEquals("objects/commercial/commercial_table", modelAndView.getViewName());
    }

    @Test
    void info() {
        ModelAndView modelAndView = commercialObjectsController.info();
        assertEquals("objects/commercial/commercial_info", modelAndView.getViewName());
    }

    @Test
    void add() {
        ModelAndView modelAndView = commercialObjectsController.add();
        assertEquals("objects/commercial/commercial_add", modelAndView.getViewName());
    }

    @Test
    void edit() {
        ModelAndView modelAndView = commercialObjectsController.edit();
        assertEquals("objects/commercial/commercial_add", modelAndView.getViewName());
    }

    @Test
    void testAdd() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        CommercialInfoDto infoDto = new CommercialInfoDto();
        CommercialMaterialAndAreaDto materialAndAreaDto = new CommercialMaterialAndAreaDto();
        CommercialAddressDto addressDto = new CommercialAddressDto();
        ResponseEntity<String> responseEntity = commercialObjectsController.add(infoDto, new BeanPropertyBindingResult(infoDto, "infoDto"),
                materialAndAreaDto, new BeanPropertyBindingResult(materialAndAreaDto, "materialAndAreaDto"),
                addressDto, new BeanPropertyBindingResult(addressDto, "addressDto"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("saveObj", responseEntity.getBody());
        verify(commercialService, times(1)).add(infoDto, materialAndAreaDto, addressDto);
    }

    @Test
    void getAll() {
        ObjectForFilterDto filterDto = new ObjectForFilterDto();
        Page<PropertyCommercialObject> page = mock(Page.class);
        when(commercialService.getAll(filterDto)).thenReturn(page);
        Page<PropertyCommercialObject> result = commercialObjectsController.getAll(filterDto);
        assertEquals(page, result);
        verify(commercialService, times(1)).getAll(filterDto);
    }

    @Test
    void getById() {
        Integer objectId = 1;
        PropertyCommercialObject object = new PropertyCommercialObject();
        when(commercialService.getById(objectId)).thenReturn(object);
        PropertyCommercialObject result = commercialObjectsController.getById(objectId);
        assertEquals(object, result);
        verify(commercialService, times(1)).getById(objectId);

    }

    @Test
    void deleteById() {
        Integer objectId = 1;
        ResponseEntity<String> responseEntity = commercialObjectsController.deleteById(objectId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("deleteObj", responseEntity.getBody());
        verify(commercialService, times(1)).deleteById(objectId);
    }

    @Test
    void getUrl() {
        Integer objectId = 1;
        PropertyCommercialObject object = new PropertyCommercialObject();
        object.setPictures(Collections.singletonList("test-url"));
        when(commercialService.getById(objectId)).thenReturn(object);
        when(minioService.getUrl("test-url")).thenReturn("http://example.com/test-url");
        ResponseEntity<String> responseEntity = commercialObjectsController.getUrl(objectId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("http://example.com/test-url", responseEntity.getBody());
        verify(commercialService, times(1)).getById(objectId);
        verify(minioService, times(1)).getUrl("test-url");
    }

    @Test
    void getFiles() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        commercialObject.setPictures(Collections.singletonList("test-url"));
        when(commercialService.getById(objectId)).thenReturn(commercialObject);
        when(minioService.getUrl("test-url")).thenReturn("http://example.com/test-url");
        when(minioService.getObjectSize("test-url")).thenReturn(1024L);
        List<CommercialObjectsController.FilesDto> result = commercialObjectsController.getFiles(objectId);
        assertEquals(1, result.size());
        assertEquals("http://example.com/test-url", result.get(0).getUrl());
        assertEquals("test-url", result.get(0).getOriginUrl());
        assertEquals("1024", result.get(0).getSize());
        verify(commercialService, times(1)).getById(objectId);
        verify(minioService, times(1)).getUrl("test-url");
        verify(minioService, times(1)).getObjectSize("test-url");
    }

    @Test
    void deletePicture() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        String url = "test-url";
        PropertyCommercialObject commercialObject = mock(PropertyCommercialObject.class);
        when(commercialService.getById(objectId)).thenReturn(commercialObject);
        ResponseEntity<String> responseEntity = commercialObjectsController.deletePicture(objectId, url);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("deleteObj", responseEntity.getBody());
        verify(commercialService, times(1)).getById(objectId);
        verify(commercialService, times(1)).save(commercialObject);
        verify(minioService, times(1)).deleteImg(url, "images");
    }

    @Test
    void deleteFile() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Integer objectId = 1;
        String url = "test-url";
        PropertyCommercialObject commercialObject = mock(PropertyCommercialObject.class);
        when(commercialService.getById(objectId)).thenReturn(commercialObject);
        ResponseEntity<String> responseEntity = commercialObjectsController.deleteFile(objectId, url);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("deleteObj", responseEntity.getBody());
        verify(commercialService, times(1)).getById(objectId);
        verify(commercialService, times(1)).save(commercialObject);
        verify(minioService, times(1)).deleteImg(url, "images");
    }

    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        commercialObjectsController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("commercialPropertyActive", true);
    }
}