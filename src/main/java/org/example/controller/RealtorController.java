package org.example.controller;

import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView editPage() {
        ModelAndView modelAndView = new ModelAndView("realtors/realtor_add");
        modelAndView.addObject("realtor", new RealtorDto());
        return modelAndView;
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("realtorsActive", true);
    }


}
