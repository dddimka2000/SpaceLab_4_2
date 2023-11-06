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

    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test")
    public String lol() {
        return "/test";
    }
    private final
    EmailService emailService;

    @PostMapping("/testEmail")
    public String sendTestEmail(){
        emailService.sendEmail("2000-dimk@ukr.net","aaaa","afasaasfasfasfasf");
        emailService.sendEmail("2000dimk@gmail.com","aaaa","afasaasfasfasfasf");
        log.info("Отправлен");
        return "redirect:/test";
    }

}
