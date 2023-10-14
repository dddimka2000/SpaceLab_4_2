package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/branches")
public class BranchController {

    @GetMapping
    public String mainPage() {
        return "branch/branch_table";
    }

    @GetMapping("/info")
    public String infoPage() {
        return "branch/branch_info";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "branch/branch_edit";
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("branchesActive", true);
    }
}
