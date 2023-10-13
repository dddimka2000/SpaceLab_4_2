package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String mainPage() {
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
