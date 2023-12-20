package org.example.controller;

import io.minio.errors.*;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.Buyer;
import org.example.entity.BuyerApplication;
import org.example.entity.BuyerApplicationEditLog;
import org.example.entity.BuyerNote;
import org.example.entity.property.type.TypeObjectForUrl;
import org.example.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyerControllerTest {
    @Mock
    private BuyerServiceImpl buyerService;

    @Mock
    private MinioService minioService;

    @Mock
    private HousesServiceImpl housesService;

    @Mock
    private PropertyInvestorObjectService propertyInvestorObjectService;

    @Mock
    private CommercialServiceImpl commercialService;

    @InjectMocks
    private BuyerController buyerController;
    @Test
    void index() {
        ModelAndView modelAndView = buyerController.index();
        assertEquals("buyer/buyer_table", modelAndView.getViewName());
    }

    @Test
    void add() {
        ModelAndView modelAndView = buyerController.add();
        assertEquals("buyer/buyer_add", modelAndView.getViewName());
    }

    @Test
    void info() {
        ModelAndView modelAndView = buyerController.info();
        assertEquals("buyer/buyer_info", modelAndView.getViewName());
    }

    @Test
    void edit() {
        ModelAndView modelAndView = buyerController.edit();
        assertEquals("buyer/buyer_add", modelAndView.getViewName());
    }

    @Test
    void getById() {
        Integer buyerId = 1;
        Buyer buyer = new Buyer();
        when(buyerService.getById(buyerId)).thenReturn(buyer);
        Buyer result = buyerController.getById(buyerId);
        assertEquals(buyer, result);
    }

    @Test
    void testAdd() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        BuyerPersonalDataDto personalDataDto = new BuyerPersonalDataDto();
        when(buyerService.addPersonalData(personalDataDto)).thenReturn(1);
        Integer result = buyerController.add(personalDataDto);
        assertEquals(1, result.intValue());
    }

    @Test
    void addApplication() {
        BuyerApplication buyerApplication = new BuyerApplication();
        ResponseEntity<String> responseEntity = buyerController.addApplication(buyerApplication);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Заявку успішно збережено", responseEntity.getBody());
        verify(buyerService, times(1)).addApplication(buyerApplication);
    }

    @Test
    void addNote() {
        BuyerNote buyerNote = new BuyerNote();
        ResponseEntity<String> responseEntity = buyerController.addNote(buyerNote);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Заметка успішно збережена", responseEntity.getBody());
        verify(buyerService, times(1)).addNote(buyerNote);
    }

    @Test
    void deleteNote() {
        Integer noteId = 1;
        ResponseEntity<String> responseEntity = buyerController.deleteNote(noteId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Замітку успішно видалено", responseEntity.getBody());
        verify(buyerService, times(1)).deleteNote(noteId);
    }

    @Test
    void deleteFile() throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        when(buyerService.getById(anyInt())).thenReturn(mock(Buyer.class));
        ResponseEntity<String> responseEntity = buyerController.deleteFile(1, "url");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Файл видалено успішно", responseEntity.getBody());
        verify(minioService, times(1)).deleteImg("url", "images");
    }

    @Test
    void getHistory() {
        List<BuyerApplicationEditLog> editHistory = mock(ArrayList.class);
        Buyer buyer = new Buyer();
        BuyerApplication buyerApplication = new BuyerApplication();
        buyerApplication.setEditHistory(editHistory);
        buyer.setApplication(buyerApplication);
        when(buyerService.getById(anyInt())).thenReturn(buyer);
        List<BuyerApplicationEditLog> result = buyerController.getHistory(1);
        assertEquals(editHistory, result);
    }

    @Test
    void getAll() {
        Integer page = 1;
        String branch = "TestBranch";
        String realtor = "TestRealtor";
        String name = "TestName";
        String phone = "TestPhone";
        String email = "TestEmail";
        String price = "TestPrice";
        Page<Buyer> buyerPage = mock(Page.class);
        when(buyerService.getAll(page, branch, realtor, name, phone, email, price)).thenReturn(buyerPage);
        Page<Buyer> result = buyerController.getAll(page, branch, realtor, name, phone, email, price);
        assertEquals(buyerPage, result);
        verify(buyerService, times(1)).getAll(page, branch, realtor, name, phone, email, price);
    }

    @Test
    void deleteById() {
        Integer buyerId = 1;
        ResponseEntity<String> responseEntity = buyerController.deleteById(buyerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Покупця видалено успішно", responseEntity.getBody());
        verify(buyerService, times(1)).deleteById(buyerId);
    }

    @Test
    void filterForObject() {
        Integer objectId = 1;
        String type = "HOUSE";
        List<Buyer> buyers = new ArrayList<>();
        when(buyerService.filterForObject(objectId, type)).thenReturn(buyers);
        List<Buyer> result = buyerController.filterForObject(objectId, type);
        assertEquals(buyers, result);
        verify(buyerService, times(1)).filterForObject(objectId, type);
    }

    @Test
    public void filterObjectSwitchCaseInvestor() {
        Integer objectId = 1;
        TypeObjectForUrl typeObjectForUrl = TypeObjectForUrl.INVESTOR;

        ModelAndView modelAndView = buyerController.filterObject(typeObjectForUrl, objectId);

        assertEquals("buyer/buyer_table", modelAndView.getViewName());
        assertEquals(propertyInvestorObjectService.findById(objectId), modelAndView.getModel().get("filterObject"));
    }

    @Test
    public void filterObjectSwitchCaseCommercial() {
        Integer objectId = 1;
        TypeObjectForUrl typeObjectForUrl = TypeObjectForUrl.COMMERCIAL;

        ModelAndView modelAndView = buyerController.filterObject(typeObjectForUrl, objectId);

        assertEquals("buyer/buyer_table", modelAndView.getViewName());
        assertEquals(commercialService.getById(objectId), modelAndView.getModel().get("filterObject"));
    }

    @Test
    public void filterObjectSwitchCaseHouse() {
        Integer objectId = 1;
        TypeObjectForUrl typeObjectForUrl = TypeObjectForUrl.HOUSE;

        ModelAndView modelAndView = buyerController.filterObject(typeObjectForUrl, objectId);

        assertEquals("buyer/buyer_table", modelAndView.getViewName());
        assertNull(modelAndView.getModel().get("filterObject"));
    }
    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        buyerController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("buyersActive", true);
    }
}