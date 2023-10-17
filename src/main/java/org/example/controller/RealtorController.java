package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/edit")
    public String editPage() {
        return "realtors/realtor_edit";
    }
}