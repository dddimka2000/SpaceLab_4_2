package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.QuestionDto;
import org.example.entity.Branch;
import org.example.entity.Question;
import org.example.service.BuyerServiceImpl;
import org.example.service.QuestionServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;

@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionServiceImpl questionService;
    private final BuyerServiceImpl buyerService;
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("questions/question_table");
    }
    @GetMapping("/get-all")
    @ResponseBody
    public Page<Question> getAll(@RequestParam("page")int page){
        return questionService.getAll(page);
    }
    @GetMapping("/getById")
    @ResponseBody
    public Question getById(@RequestParam("id")int id){
        return questionService.getById(id);
    }
    @PostMapping("/edit")
    public ResponseEntity<String> edit(@ModelAttribute @Valid QuestionDto questionDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())return ResponseEntity.ok().body(bindingResult.getAllErrors().get(0).toString());
        questionService.edit(questionDto);
        return ResponseEntity.ok().body("Відповідь відредагована успішно");
    }
}
