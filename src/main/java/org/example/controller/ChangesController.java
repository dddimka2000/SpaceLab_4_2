package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/changes")
public class ChangesController {

    @GetMapping
    public String mainPage() {
        return "changes/changes_table";
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("changesActive", true);
    }
}
