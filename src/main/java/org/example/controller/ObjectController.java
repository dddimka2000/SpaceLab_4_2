package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.repository.PropertyObjectRepository;
import org.example.service.PropertyObjectServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/object")
@RequiredArgsConstructor
public class ObjectController {
    private final PropertyObjectServiceImpl propertyObjectService;
    @GetMapping("/checkCode")
    public ResponseEntity<Boolean> checkCode(@RequestParam String code){
        return ResponseEntity.ok().body(propertyObjectService.checkCode(code.replace(" ", "")));
    }
}
