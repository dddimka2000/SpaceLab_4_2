package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.example.entity.property.type.ContactType;
import org.example.service.MinioService;
import org.example.service.RealtorContactServiceImpl;
import org.example.service.RealtorFeedBackServiceImpl;
import org.example.service.RealtorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealtorControllerTest {
    @InjectMocks
    private RealtorController realtorController;
    @Mock
    private RealtorServiceImpl realtorService;
    @Mock
    private RealtorFeedBackServiceImpl realtorFeedBackService;
    @Mock
    private RealtorContactServiceImpl realtorContactService;
    @Mock
    private MinioService minioService;
    @Test
    void mainPage() {
        ModelAndView result = realtorController.mainPage();
        assertEquals("realtors/realtors_table", result.getViewName());
    }

    @Test
    void getCountByCode() {
        when(realtorService.countByCode(123)).thenReturn(7);
        ResponseEntity<Integer> result = realtorController.getCountByCode(123);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(7, result.getBody());
    }

    @Test
    void infoPage() {
        Realtor realtor = new Realtor();
        realtor.setId(1);
        when(realtorService.getById(1)).thenReturn(realtor);
        ModelAndView result = realtorController.infoPage(1);
        assertEquals("realtors/realtor_info", result.getViewName());
        assertEquals(realtor, result.getModel().get("realtor"));
    }

    @Test
    void addPage() {
        ModelAndView result = realtorController.addPage();
        assertEquals("realtors/realtor_add", result.getViewName());
        assertEquals(new RealtorDto(), result.getModel().get("realtorDto"));
    }

    @Test
    void editPage() {
        Realtor realtor = new Realtor();
        realtor.setId(1);
        when(realtorService.getById(1)).thenReturn(realtor);
        ModelAndView result = realtorController.editPage(1);
        assertEquals("realtors/realtor_add", result.getViewName());
        assertEquals(realtor, result.getModel().get("realtorDto"));
    }

    @Test
    void testAddPage() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        RealtorDto realtorDto = new RealtorDto();
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(false);
//        ResponseEntity<String> result = realtorController.addPage(realtorDto, bindingResult);
//        assertEquals("saveObj", result.getBody());
//        verify(realtorService, times(1)).add(realtorDto);
    }
    @Test
    void testAddPageWithInvalidDate() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        RealtorDto realtorDto = new RealtorDto();
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError("realtorDto", "field", "message")));
//        ResponseEntity<String> result = realtorController.addPage(realtorDto, bindingResult);
//        assertEquals("ERROR(field): message", result.getBody());
//        verify(realtorService, never()).add(realtorDto);
    }

    @Test
    void deleteById() {
        ResponseEntity<String> result = realtorController.deleteById(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("deleteObj", result.getBody());
    }

    @Test
    void getAll() {
        Realtor realtor = new Realtor();
        realtor.setId(1);
        when(realtorService.getAll(1, 5,"branchId", "code", "fullName", "phone", "email", "2020-02-02", "2020-02-04")).thenReturn(new PageImpl<>(Collections.singletonList(realtor)));
        Page<Realtor> result = realtorController.getAll(1,5, "branchId", "code", "fullName", "phone", "email", "2020-02-02","2020-02-04");
        assertEquals(1, result.getTotalElements());
        assertEquals(realtor, result.getContent().get(0));
    }

    @Test
    void getUrl() {
        when(minioService.getUrl("test-url")).thenReturn("test-url");
        String result = realtorController.getUrl("test-url");
        assertEquals("test-url", result);
    }

    @Test
    void uploadFile() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Realtor realtor = mock(Realtor.class);
        when(realtorService.getById(anyInt())).thenReturn(realtor);
        ResponseEntity<String> result = realtorController.uploadFile(any(MultipartFile.class), 1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void delete() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        lenient().when(minioService.getUrl("test-url")).thenReturn("test-url");
        Realtor realtor = new Realtor();
        realtor.setFiles(new ArrayList<>(List.of("test-url")));
        when(realtorService.getById(anyInt())).thenReturn(realtor);
        ResponseEntity<String> result = realtorController.delete("test-url", 1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("deleteObj", result.getBody());
        assertEquals(Collections.emptyList(), realtor.getFiles());
    }

    @Test
    void testDelete() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseEntity<String> result = realtorController.delete(1);
        verify(realtorFeedBackService).deleteById(1);
        assertEquals("deleteObj", result.getBody());
    }

    @Test
    void deleteContact() {
        ResponseEntity<String> result = realtorController.deleteContact(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("deleteObj", result.getBody());
    }

    @Test
    void save() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseEntity<String> result = realtorController.save(1, "John Doe", "123456789", "Some description");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("saveObj", result.getBody());
    }

    @Test
    void downloadFile() throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        when(minioService.getPhoto("test-url", "images")).thenReturn("Hello, World!".getBytes());
        byte[] result = realtorController.downloadFile("test-url");
        assertEquals("Hello, World!", new String(result));
    }

    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        realtorController.activeMenuItem(model);
        Mockito.verify(model, times(1)).addAttribute("realtorsActive", true);
    }

    @Test
    void search() {
        when(realtorService.forSelect("test-name", PageRequest.of(1, 10, Sort.by(Sort.Order.asc("id")))))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Realtor())));
        Page<Realtor> result = realtorController.search("test-name", 1, 10);
        assertEquals(1, result.getTotalElements());
        assertEquals(new Realtor(), result.getContent().get(0));
    }

    @Test
    void getById() {
        when(realtorService.getById(1)).thenReturn(new Realtor());
        Realtor result = realtorController.getById(1);
        assertNotNull(result);
    }
    @Test
    void getByIdWithException() {
        when(realtorService.getById(1)).thenThrow(new EntityNotFoundException());
        Realtor result = realtorController.getById(1);
        assertNull(result);
    }

    @Test
    void getByCode() {
        when(realtorService.getByCode(123)).thenReturn(new Realtor());
        Realtor result = realtorController.getByCode(123);
        assertNotNull(result);
    }
    @Test
    void getByCodeWithException() {
        when(realtorService.getByCode(123)).thenThrow(new EntityNotFoundException());
        Realtor result = realtorController.getByCode(123);
        assertNull(result);
    }
}