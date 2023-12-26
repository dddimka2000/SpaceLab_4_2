package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.property._PropertyObject;
import org.example.repository.RealtorContactRepository;
import org.example.repository.RealtorRepository;
import org.example.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/valid")
@RequiredArgsConstructor
public class ValidController {
    private final RealtorContactServiceImpl contactService;
    private final BranchServiceImpl branchService;
    private final PropertyObjectServiceImpl objectService;
    private final BuyerServiceImpl buyerService;

    @GetMapping("/phone")
    public ResponseEntity<Boolean> checkPhone(@RequestParam String phone){
        boolean result = false;
        if(contactService.existsByPhone(phone))result = true;
        if(branchService.existsByPhone(phone))result = true;
        if(objectService.existsByPhone(phone))result = true;
        if(buyerService.existsByPhone(phone))result = true;
        return ResponseEntity.ok().body(result);
    }
}
