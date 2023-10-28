package org.example.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.PropertyInvestorObjectDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/investor_objects")
@Log4j2
public class ObjectsInvestorController {

    @GetMapping
    public ModelAndView showObjectsInvestors() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/objectsInvestors");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView newObjectsInvestorControllerPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/objects/object_investor/newObjectInvestor");
        return modelAndView;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity newObjectsInvestorControllerPost(@Valid @ModelAttribute PropertyInvestorObjectDTO propertyInvestorObjectDTO, BindingResult bindingResult) {
        log.info(propertyInvestorObjectDTO);
        propertyInvestorObjectDTO.getFiles().stream().forEach(s->log.info(s.getOriginalFilename()));
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.ok().body("ok");
    }
}
