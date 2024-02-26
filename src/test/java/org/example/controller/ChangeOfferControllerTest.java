package org.example.controller;

import org.example.entity.ChangeOffer;
import org.example.service.ChangeOfferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeOfferControllerTest {
    @Mock
    private ChangeOfferServiceImpl changeOfferService;

    @InjectMocks
    private ChangeOfferController changeOfferController;

    @Test
    void index() {
        ModelAndView modelAndView = changeOfferController.index();
        assertEquals("proposals_for_changes/changes_table", modelAndView.getViewName());
    }

//    @Test
//    void getAll() {
//        int page = 1;
//        Page<ChangeOffer> changeOfferPage = mock(Page.class);
//        when(changeOfferService.getAll(page)).thenReturn(changeOfferPage);
//        Page<ChangeOffer> result = changeOfferController.getAll(page);
//        assertEquals(changeOfferPage, result);
//        verify(changeOfferService, times(1)).getAll(page);
//    }

    @Test
    void getById() {
        Integer changeOfferId = 1;
        ChangeOffer changeOffer = new ChangeOffer();
        when(changeOfferService.getById(changeOfferId)).thenReturn(changeOffer);
        ChangeOffer result = changeOfferController.getById(changeOfferId);
        assertEquals(changeOffer, result);
        verify(changeOfferService, times(1)).getById(changeOfferId);
    }

    @Test
    void deleteById() {
        Integer changeOfferId = 1;
        changeOfferController.deleteById(changeOfferId);
        verify(changeOfferService, times(1)).deleteById(changeOfferId);
    }

    @Test
    void activeMenuItem() {
        Model model = mock(Model.class);
        changeOfferController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("changesActive", true);
    }
}