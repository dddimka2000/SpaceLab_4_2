package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.PotentialCustomerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class PotentialCustomerController {
    private final PotentialCustomerServiceImpl potentialCustomerService;
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("potentialCustomers", true);
    }
}