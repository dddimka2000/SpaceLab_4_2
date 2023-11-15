package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/commercial")
@Log4j2
public class CommercialObjectsController {
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("objects/commercial/commercial_table");
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("objects/commercial/commercial_add");
    }
}
