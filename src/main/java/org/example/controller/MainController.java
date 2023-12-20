package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Log4j2
public class MainController {
    final
    PropertyInvestorObjectService propertyInvestorObjectService;
    final
    PropertySecondaryObjectService propertySecondaryObjectService;
    final
    ObjectBuilderService objectBuilderService;
    final
    CommercialServiceImpl commercialService;
    final
    HousesServiceImpl housesService;
    final
    QuestionServiceImpl questionService;
    final
    ChangeOfferServiceImpl changeOfferService;
    final
    BranchServiceImpl branchService;


    public MainController(HousesServiceImpl housesService, ObjectBuilderService objectBuilderService, CommercialServiceImpl commercialService, PropertyInvestorObjectService propertyInvestorObjectService, PropertySecondaryObjectService propertySecondaryObjectService, QuestionServiceImpl questionService, ChangeOfferServiceImpl changeOfferService, BranchServiceImpl branchService) {
        this.housesService = housesService;
        this.objectBuilderService = objectBuilderService;
        this.commercialService = commercialService;
        this.propertyInvestorObjectService = propertyInvestorObjectService;
        this.propertySecondaryObjectService = propertySecondaryObjectService;
        this.questionService = questionService;
        this.changeOfferService = changeOfferService;
        this.branchService = branchService;
    }

    @GetMapping("/")
    public String local(Model model) {
        model.addAttribute("questions", questionService.count());
        model.addAttribute("changeOffer", changeOfferService.count());
        model.addAttribute("branches", branchService.count());
        model.addAttribute("Builder", objectBuilderService.count());
        model.addAttribute("Investor", propertyInvestorObjectService.count());
        model.addAttribute("Secondary", propertySecondaryObjectService.count());
        model.addAttribute("Commercial", commercialService.count());
        model.addAttribute("Houses", housesService.count());
        return "/mainAdmin";
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("mainPageActive", true);
    }
}