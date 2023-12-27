package org.example.controller;

import org.example.entity.RealtorFeedBack;
import org.example.service.RealtorFeedBackServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private RealtorFeedBackServiceImpl feedBackService;

    @InjectMocks
    private ApplicationController applicationController;

    @Test
    public void modelAndView() {
        ModelAndView modelAndView = applicationController.modelAndView();

        assertEquals("applications/applications_table", modelAndView.getViewName());
    }

    @Test
    public void getAll() {
        List<RealtorFeedBack> feedbackList = new ArrayList<>();
        when(feedBackService.getAll()).thenReturn(feedbackList);

        List<RealtorFeedBack> result = applicationController.getAll();

        assertEquals(feedbackList, result);
        verify(feedBackService, times(1)).getAll();
    }

    @Test
    public void deleteByID() {
        Integer idToDelete = 1;

        ResponseEntity<String> responseEntity = applicationController.deleteByID(idToDelete);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("deleteObj", responseEntity.getBody());
        verify(feedBackService, times(1)).deleteById(idToDelete);
    }

    @Test
    public void activeMenuItem() {
        Model model = mock(Model.class);

        applicationController.activeMenuItem(model);

        verify(model, times(1)).addAttribute("applicationsActive", true);
    }
}
