package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {
    @GetMapping("/role")
    @ResponseBody
    public List<UserRole> getRoles(@RequestParam("_type") String page){
        return List.of(UserRole.values());
    }
}
