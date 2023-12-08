package org.example.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.PageEntityDto;
import org.example.entity.PageEntity;
import org.example.mapper.PageMapper;
import org.example.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/pages")
@Log4j2
public class PageController {
    private final
    PageService pageService;


    public PageController(PageService pageService) {
        this.pageService = pageService;
    }



    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("pages", pageService.findAll().stream().map(s -> PageMapper.INSTANCE.toDto(s)).collect(Collectors.toList()));
        log.info(pageService.findAll().stream().map(s -> PageMapper.INSTANCE.toDto(s)).collect(Collectors.toList()));
        return "pages/pages_table";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        if (pageService.findById(id).isEmpty()) {
            return "/error/404";
        }
        model.addAttribute("page", PageMapper.INSTANCE.toDto(pageService.findById(id).get()));

        return "pages/page_edit";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity editPage(@PathVariable Integer id, @Valid @ModelAttribute PageEntityDto pageEntityDto, BindingResult bindingResult) {
        PageEntity page=pageService.findById(id).get();
        log.info(pageEntityDto);
        if(bindingResult.hasErrors()){
            log.error("error validation");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        page=PageMapper.INSTANCE.toEntity(pageEntityDto);
        pageService.save(page);
        return ResponseEntity.ok().body("ok");
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("pagesActive", true);
    }
}
