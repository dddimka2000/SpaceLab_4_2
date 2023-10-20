package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.repository.BranchRepository;
import org.example.service.BranchService;
import org.example.validator.BranchValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/branches")
@RequiredArgsConstructor
@Log4j2
public class BranchController {
    private final BranchService branchService;
    private final BranchValidator branchValidator;
    @GetMapping
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("branch/branch_table");
        return modelAndView;
    }

    @GetMapping("/info")
    public ModelAndView infoPage() {
        ModelAndView modelAndView = new ModelAndView("branch/branch_info");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("branch/branch_add");
        modelAndView.addObject("branchDto", new BranchDto());
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")Long id) {
        ModelAndView modelAndView = new ModelAndView("branch/branch_add");
        modelAndView.addObject("branchDto", branchService.getById(id));
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createPage(@ModelAttribute("branchDto") @Valid BranchDto branchDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ModelAndView modelAndView = new ModelAndView();
        if(branchDto.getId() == null || !branchDto.getImgPath().isEmpty())
            branchValidator.validate(branchDto, bindingResult);
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("branch/branch_add");
        }else {
            modelAndView.setViewName("redirect:/branches");
            branchService.add(branchDto);
        }
        return modelAndView;
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("branchesActive", true);
    }
    @GetMapping("/getAll/{page}")
    @ResponseBody
    public Page<Branch> getAll(@PathVariable("page")int page){
        return branchService.getAll(page);
    }

    @GetMapping("/filter")
    @ResponseBody
    public Page<Branch> getAll(@RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("page")int page){
        return branchService.getAll(page, name, address);
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id")long id){
        branchService.deleteById(id);
        return ResponseEntity.ok("Branch was deleted");
    }
}
