package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("buyer/buyer_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("buyer/buyer_edit");
    }
}
