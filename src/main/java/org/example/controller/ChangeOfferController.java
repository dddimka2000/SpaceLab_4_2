package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.ChangeOffer;
import org.example.entity.Question;
import org.example.service.ChangeOfferServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.SecureRandom;

@Controller
@RequiredArgsConstructor
@RequestMapping("/proposals_for_changes")
public class ChangeOfferController {
    private final ChangeOfferServiceImpl changeOfferService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("proposals_for_changes/changes_table");
    }

    @GetMapping("/get-all")
    @ResponseBody
    public Page<ChangeOffer> getAll(@RequestParam("page") int page,@RequestParam("size") int size) {
        return changeOfferService.getAll(page,size);
    }

    @GetMapping("/getById")
    @ResponseBody
    public ChangeOffer getById(@RequestParam Integer id) {
        return changeOfferService.getById(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable Integer id) {
        changeOfferService.deleteById(id);
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("changesActive", true);
    }
}
