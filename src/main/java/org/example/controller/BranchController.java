package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.service.BranchServiceImpl;
import org.example.util.validator.BranchValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/branches")
@RequiredArgsConstructor
@Log4j2
public class BranchController {
    private final BranchServiceImpl branchServiceImpl;
    private final BranchValidator branchValidator;

    @GetMapping
    public ModelAndView mainPage() {
        return new ModelAndView("branch/branch_table");
    }

    @GetMapping("/{id}")
    public ModelAndView infoPage(@PathVariable("id") int id) {
        return new ModelAndView("branch/branch_info", "branch", branchServiceImpl.getById(id));
    }

    @GetMapping("/create")
    public ModelAndView createPage() {
        return new ModelAndView("branch/branch_add", "branchDto", new BranchDto());
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id) {
        return new ModelAndView("branch/branch_add", "branchDto", branchServiceImpl.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPage(@ModelAttribute @Valid BranchDto branchDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        branchValidator.validate(branchDto, bindingResult);
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResponseEntity.ok().body("ERROR(" + fieldError.getField() + "): " + fieldError.getDefaultMessage());
        }
        branchServiceImpl.add(branchDto);
        return ResponseEntity.ok().body("Об'єкт успішно збережено");
    }


    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("branchesActive", true);
    }

    @GetMapping("/get-all")
    @ResponseBody
    public Page<Branch> getAll(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("page") int page) {
        return branchServiceImpl.getAll(page, code, name, address);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
        branchServiceImpl.deleteById(id);
        return ResponseEntity.ok("Філіал був видалений");
    }

    @GetMapping("/for/select")
    @ResponseBody
    public Page<Branch> search(@RequestParam("query") String name,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        Page<Branch> searchResults = branchServiceImpl.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }

    @GetMapping("/countCode/{code}")
    public ResponseEntity<Integer> getCountByCode(@PathVariable Integer code) {
        return ResponseEntity.ok().body(branchServiceImpl.countByCode(code));
    }
}
