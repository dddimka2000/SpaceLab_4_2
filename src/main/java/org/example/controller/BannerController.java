package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/banners")
public class BannerController {

    @GetMapping
    public String mainPage() {
        return "banners/banners_table";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "banners/banners_edit";
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("bannersActive", true);
    }
}
