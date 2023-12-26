package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.RealtorFeedBack;
import org.example.service.RealtorFeedBackServiceImpl;
import org.example.service.RealtorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final RealtorFeedBackServiceImpl feedBackService;
    @GetMapping
    public ModelAndView modelAndView(){
        return new ModelAndView("applications/applications_table");
    }
    @GetMapping("/getAll")
    @ResponseBody
    public List<RealtorFeedBack> getAll(){
        return feedBackService.getAll();
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable Integer id){
        feedBackService.deleteById(id);
        return ResponseEntity.ok().body("deleteObj");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("applicationsActive", true);
    }
}
