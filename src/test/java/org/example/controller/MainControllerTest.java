package org.example.controller;

import org.example.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @Mock
    private Model model;

    @Mock
    private HousesServiceImpl housesService;

    @Mock
    private ObjectBuilderService objectBuilderService;

    @Mock
    private CommercialServiceImpl commercialService;

    @Mock
    private PropertyInvestorObjectService propertyInvestorObjectService;

    @Mock
    private PropertySecondaryObjectService propertySecondaryObjectService;

    @Mock
    private QuestionServiceImpl questionService;

    @Mock
    private ChangeOfferServiceImpl changeOfferService;

    @Mock
    private BranchServiceImpl branchService;

    @InjectMocks
    private MainController mainController;

    @Test
    public void testLocal() {
        when(questionService.count()).thenReturn(5L);
        when(changeOfferService.count()).thenReturn(10L);
        when(branchService.count()).thenReturn(3L);
        when(objectBuilderService.count()).thenReturn(8L);
        when(propertyInvestorObjectService.count()).thenReturn(15L);
        when(propertySecondaryObjectService.count()).thenReturn(7L);
        when(commercialService.count()).thenReturn(12L);
        when(housesService.count()).thenReturn(20L);
        String result = mainController.local(model);
        assertEquals("/mainAdmin", result);
        assertEquals("/mainAdmin", result);
        verify(model, times(1)).addAttribute("questions", 5L);
        verify(model, times(1)).addAttribute("changeOffer", 10L);
        verify(model, times(1)).addAttribute("branches", 3L);
        verify(model, times(1)).addAttribute("Builder", 8L);
        verify(model, times(1)).addAttribute("Investor", 15L);
        verify(model, times(1)).addAttribute("Secondary", 7L);
        verify(model, times(1)).addAttribute("Commercial", 12L);
        verify(model, times(1)).addAttribute("Houses", 20L);
    }

    @Test
    public void testActiveMenuItem() {
        Model model = mock(Model.class);
        mainController.activeMenuItem(model);
        verify(model, times(1)).addAttribute("mainPageActive", true);
    }
}