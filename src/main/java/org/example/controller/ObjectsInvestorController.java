package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/investor_objects")
@Log4j2
public class ObjectsInvestorController {

    @GetMapping
    public ModelAndView showObjectsInvestors() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/objectsInvestors");
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView editPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/newObjectInvestor");
        return modelAndView;
    }

}
