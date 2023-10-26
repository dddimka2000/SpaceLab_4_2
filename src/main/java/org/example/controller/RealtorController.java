package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.RealtorDto;
import org.example.entity.Branch;
import org.example.entity.Realtor;
import org.example.entity.property.type.ContactType;
import org.example.service.MinioService;
import org.example.service.RealtorServiceImpl;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Controller
@RequestMapping("/realtors")
@RequiredArgsConstructor
public class RealtorController {
    private final RealtorServiceImpl realtorService;
    private final MinioService minioService;
    @GetMapping
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("realtors/realtors_table");
        return modelAndView;
    }

    @GetMapping("/info")
    public String infoPage() {
        return "realtors/realtor_info";
    }

    @GetMapping("/add")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView("realtors/realtor_add");
        modelAndView.addObject("realtorDto", new RealtorDto());
        modelAndView.addObject("contactTypes", ContactType.values());
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("realtors/realtor_add");
        modelAndView.addObject("realtorDto", realtorService.getById(id));
        modelAndView.addObject("contactTypes", ContactType.values());
        return modelAndView;
    }
    @PostMapping("/add")
    public ModelAndView addPage(@ModelAttribute @Valid RealtorDto realtorDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ModelAndView modelAndView = new ModelAndView("redirect:/realtors");
        if(bindingResult.hasErrors()){
            modelAndView.addObject("contactTypes", ContactType.values());
            modelAndView.addObject("realtorDto", realtorDto);
            modelAndView.setViewName("realtors/realtor_add");
            return modelAndView;
        }
        realtorService.add(realtorDto);
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id")int id){
        realtorService.deleteById(id);
        return ResponseEntity.ok("Realtor was deleted");
    }
    @GetMapping("/get-all")
    @ResponseBody
    public Page<Realtor> getAll(@RequestParam("page")int page, @RequestParam("filial")String branchId, @RequestParam("code")String code, @RequestParam("fullName")String fullName, @RequestParam("phone")String phone, @RequestParam("email")String email, @RequestParam("birthdate") String birthdate){
        return realtorService.getAll(page, branchId, code, fullName, phone, email, birthdate);
    }
    @GetMapping("/getUrl")
    @ResponseBody
    public String getUrl(@RequestParam("url")String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioService.getUrl(url);
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("realtorsActive", true);
    }


}
