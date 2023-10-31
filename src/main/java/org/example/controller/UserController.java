package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.RealtorDto;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("usersActive", true);
        return "users/users_table";
    }

    @GetMapping("/info")
    public String userInfo() {
        return "users/user_info";
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("users/user_add");
        modelAndView.addObject("userDto", new UserEntity());
        return modelAndView;
    }
    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> add(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult){
        return ResponseEntity.ok().body("Сохранено");
    }
    @GetMapping("/get/id/{id}")
    public UserEntity getById(@PathVariable("id")int id){
        return userService.getById(id);
    }
}
