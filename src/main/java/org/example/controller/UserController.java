package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("usersActive", true);
        return "users/users_table";
    }

    @GetMapping("/info")
    public String userInfo() {
        return "users/user_info";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "users/user_edit";
    }
}
