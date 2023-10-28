package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.example.entity.property.type.ContactType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/realtors")
public class RealtorController {

    @GetMapping
    public String mainPage() {
        return "realtors/realtors_table";
    }

    @GetMapping("/info")
    public String infoPage() {
        return "realtors/realtor_info";
    }

    @GetMapping("/add")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView("realtors/realtor_add");
        modelAndView.addObject("realtorDto", new RealtorDto());
        modelAndView.addObject("contactTypes", ContactType.values());
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addPage(@ModelAttribute @Valid RealtorDto realtorDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("contactTypes", ContactType.values());
            modelAndView.addObject("realtorDto", realtorDto);
            modelAndView.setViewName("realtors/realtor_add");
            return modelAndView;
        }
        modelAndView.setViewName("realtors/realtor_add");
        modelAndView.addObject("realtorDto", new RealtorDto());
        return modelAndView;
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("realtorsActive", true);
    }


}
