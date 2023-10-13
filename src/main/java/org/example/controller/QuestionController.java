package org.example.controller;

import org.example.entity.Question;
import org.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    final
    QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    int pageSize=10;
    @GetMapping("/questions")
    public String questionsAdminPage(Model model, @RequestParam(defaultValue = "0", name = "page") Integer page){
        Page<Question> questionPage=questionService.findAllQuestionPages(page,10);
        List<Question> orderTableEntityList = new ArrayList<>();
        int totalPage = 0;
        if (questionPage != null) {
            totalPage = questionPage.getTotalPages();
            orderTableEntityList = questionPage.getContent();
        }
        model.addAttribute("orderTableEntityList", orderTableEntityList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);
        //TODO забросил
        return "questions";
    }

}
