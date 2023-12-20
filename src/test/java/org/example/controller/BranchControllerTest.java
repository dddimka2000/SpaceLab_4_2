package org.example.controller;

import io.minio.errors.*;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.service.BranchServiceImpl;
import org.example.util.validator.BranchValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    @Mock
    private BranchServiceImpl branchService;

    @Mock
    private BranchValidator branchValidator;

    @InjectMocks
    private BranchController branchController;

    @Test
    void mainPage() {
        ModelAndView modelAndView = branchController.mainPage();
        assertEquals("branch/branch_table", modelAndView.getViewName());

    }

    @Test
    void infoPage() {
        int branchId = 1;
        Branch branch = mock(Branch.class);
        when(branchService.getById(branchId)).thenReturn(branch);
        ModelAndView modelAndView = branchController.infoPage(branchId);
        assertEquals("branch/branch_info", modelAndView.getViewName());
        assertEquals(branch, modelAndView.getModel().get("branch"));
    }

    @Test
    void createPage() {
        ModelAndView modelAndView = branchController.createPage();
        assertEquals("branch/branch_add", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("branchDto"));
    }
    @Test
    void edit() {
        int branchId = 1;
        Branch branch = mock(Branch.class);
        when(branchService.getById(branchId)).thenReturn(branch);
        ModelAndView modelAndView = branchController.edit(branchId);
        assertEquals("branch/branch_add", modelAndView.getViewName());
        assertEquals(branch, modelAndView.getModel().get("branchDto"));
    }

    @Test
    void testCreatePage() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        BranchDto branchDto = new BranchDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<String> responseEntity = branchController.createPage(branchDto, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Об'єкт успішно збережено", responseEntity.getBody());
        verify(branchValidator, times(1)).validate(branchDto, bindingResult);
        verify(branchService, times(1)).add(branchDto);

    }
    @Test
    public void createPageWithInvalidData() throws Exception {
        BranchDto branchDto = new BranchDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError(anyString(), "some message", anyString())));

        ResponseEntity<String> responseEntity = branchController.createPage(branchDto, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("ERROR"));
        verify(branchValidator, times(1)).validate(branchDto, bindingResult);
        verify(branchService, never()).add(branchDto);
    }
    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        branchController.activeMenuItem(model);
        verify(model, times(1)).addAttribute(eq("branchesActive"), eq(true));
    }

    @Test
    void getAll() {
        String code = "123";
        String name = "Test Branch";
        String address = "Test Address";
        int page = 1;
        Page<Branch> branchPage = mock(Page.class);
        when(branchService.getAll(page, code, name, address)).thenReturn(branchPage);
        Page<Branch> result = branchController.getAll(code, name, address, page);
        assertEquals(branchPage, result);
        verify(branchService, times(1)).getAll(page, code, name, address);
    }

    @Test
    void deleteById() {
        int idToDelete = 1;
        ResponseEntity<String> responseEntity = branchController.deleteById(idToDelete);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Філіал був видалений", responseEntity.getBody());
        verify(branchService, times(1)).deleteById(idToDelete);
    }

    @Test
    void search() {
        String query = "TestQuery";
        int page = 1;
        int size = 10;
        Page<Branch> searchResults = mock(Page.class);
        when(branchService.forSelect(query, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))))).thenReturn(searchResults);
        Page<Branch> result = branchController.search(query, page, size);
        assertEquals(searchResults, result);
        verify(branchService, times(1)).forSelect(query, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
    }

    @Test
    void getCountByCode() {
        Integer code = 123;
        when(branchService.countByCode(code)).thenReturn(5);
        ResponseEntity<Integer> responseEntity = branchController.getCountByCode(code);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(5, responseEntity.getBody().intValue());
        verify(branchService, times(1)).countByCode(code);
    }
}