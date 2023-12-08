package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class TestController {

    @GetMapping("/test")
    public String lol() {
        return "/test";
    }


}
