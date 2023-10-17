package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PageController {

    @GetMapping
    public String mainPage() {
        return "pages/pages_table";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "pages/page_edit";
    }
}