package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.entity.UserEntity;
import org.example.service.EmailService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Log4j2
public class RestorePasswordController {
    private  final
    UserDetailsServiceImpl userDetailsService;
    private final
    EmailService emailService;


    public RestorePasswordController(UserDetailsServiceImpl userDetailsService, EmailService emailService) {
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @GetMapping("/auth/login/restore_password")
    public String restore_password(){
        return"/auth/restorePass";
    }
    @PostMapping("/auth/login/restore_password")
    public ResponseEntity<?> restore_password(@RequestParam("email") String email){
        Optional<UserEntity> user=userDetailsService.findByEmail(email);
        if(user.isEmpty()){
            log.info("Doesn't  exist user with so E-mail :"+ email);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Не существует пользователя с таким E-mail");
            return ResponseEntity.badRequest().body(response);
        }
        log.info("Send E-mail: "+ email);
        String code=emailService.generateRandomPassword(20,40);
        emailService.sendEmail(email,"Восстановление пароля", code);
        user.get().setCodeRestorePassword(code);
        userDetailsService.save(user.get());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Код отправлен");
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/auth/login/checkCode")
    public ResponseEntity<?> checkCode(@RequestParam("code") String code,@RequestParam("email") String email){
        Optional<UserEntity> user=userDetailsService.findByEmail(email);
        if(user.isEmpty() || !code.equals(user.get().getCodeRestorePassword())){
            log.info("Doesn't  exist user with so E-mail: "+ email+ "\n или кодом :"+ code);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Код отличается от отправленного на почту");
            return ResponseEntity.badRequest().body(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Код подтвержден");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/auth/login/newPassword")
    public ResponseEntity<?> checkCode(@RequestParam("code") String code,@RequestParam("email") String email,@RequestParam("newPassword") String newPassword){
        Optional<UserEntity> user=userDetailsService.findByEmail(email);
        if(user.isEmpty() || !code.equals(user.get().getCodeRestorePassword())){
            log.info("Doesn't exist user with this e-mail: "+ email+ "\n or code :"+ code);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Код отличается от отправленного на почту");
            return ResponseEntity.badRequest().body(response);
        }
        Map<String, String> response = new HashMap<>();
        user.get().setPassword(newPassword);
        userDetailsService.save(user.get());
        response.put("message", "Пароль изменен");
        return ResponseEntity.ok().body(response);
    }
}
