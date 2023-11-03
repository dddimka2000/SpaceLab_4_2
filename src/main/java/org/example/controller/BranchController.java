package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.service.BranchService;
import org.example.util.validator.BranchValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/*

fixme

use /admin/... for admin panel application
use /cabinet/... for realtor cabinet application

bucket names should be taken from application properties

use @RequiredArgsConstructor and format code to make smaller files

attach mappers through @Autowired

Move exception handling and optional checking from here to Service Layer

 */

@Controller
@RequestMapping("/admin/branches")
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

    /*
    fixme
    alternate approach in one line
    return new ModelAndView('banners/banners_table', 'banners', bannerService.findAll());
     */

    @GetMapping("/{id}")
    public ModelAndView infoPage(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("branch/branch_info");
        modelAndView.addObject("branch", branchService.getById(id));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("branch/branch_add");
        modelAndView.addObject("branchDto", new BranchDto());
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("branch/branch_add");
        modelAndView.addObject("branchDto", branchService.getById(id));
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createPage(@ModelAttribute("branchDto") @Valid BranchDto branchDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ModelAndView modelAndView = new ModelAndView();

        //fixme
        //why is checking id and img path separate from validation
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

    // fixme
    // @RequestParam("code") String code - remove ("code"), no need to repeat
    @GetMapping("/get-all")
    @ResponseBody
    public Page<Branch> getAll(@RequestParam("code")String code, @RequestParam("name")String name, @RequestParam("address")String address, @RequestParam("page")int page){
        return branchService.getAll(page, code, name, address);
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id")int id){
        branchService.deleteById(id);
        return ResponseEntity.ok("Branch was deleted");
    }

    // fixme strange/url/naming/approach
    @GetMapping("/for/select")
    @ResponseBody
    public Page<Branch> search(@RequestParam("query") String name,
                                @RequestParam("page") int page,
                                @RequestParam("size") int size) {
        Page<Branch> searchResults = branchService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }
}
