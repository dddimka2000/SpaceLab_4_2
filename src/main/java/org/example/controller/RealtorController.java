package org.example.controller;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.RealtorDto;
import org.example.entity.Branch;
import org.example.entity.Realtor;
import org.example.entity.RealtorFeedBack;
import org.example.entity.property.type.ContactType;
import org.example.service.MinioService;
import org.example.service.RealtorContactServiceImpl;
import org.example.service.RealtorFeedBackServiceImpl;
import org.example.service.RealtorServiceImpl;
import org.springframework.boot.Banner;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/realtors")
@RequiredArgsConstructor
@Log4j2
public class RealtorController {
    private final RealtorServiceImpl realtorService;
    private final RealtorFeedBackServiceImpl realtorFeedBackService;
    private final RealtorContactServiceImpl realtorContactService;
    private final MinioService minioService;
    @GetMapping
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView("realtors/realtors_table");
        return modelAndView;
    }
    @GetMapping("/countCode/{code}")
    public ResponseEntity<Integer> getCountByCode(@PathVariable Integer code){
        return ResponseEntity.ok().body(realtorService.countByCode(code));
    }
    @GetMapping("/{id}")
    public ModelAndView infoPage(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("realtors/realtor_info");
        modelAndView.addObject("realtor", realtorService.getById(id));
        return modelAndView;
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
    public ResponseEntity<String> addPage(@ModelAttribute @Valid RealtorDto realtorDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            return ResponseEntity.ok().body("ERROR(" + fieldError.getField() + "): " + fieldError.getDefaultMessage());
        }
        realtorService.add(realtorDto);
        return ResponseEntity.ok().body("saveObj");
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteById(@PathVariable("id")int id){
        realtorService.deleteById(id);
        return ResponseEntity.ok("deleteObj");
    }
    @GetMapping("/get-all")
    @ResponseBody
    public Page<Realtor> getAll(@RequestParam("page")int page, @RequestParam("filial")String branchId, @RequestParam("code")String code, @RequestParam("fullName")String fullName, @RequestParam("phone")String phone, @RequestParam("email")String email, @RequestParam("birthdate") String birthdate){
        return realtorService.getAll(page, branchId, code, fullName, phone, email, birthdate);
    }
    @GetMapping("/getUrl")
    @ResponseBody
    public String getUrl(@RequestParam("url")String url){
        return minioService.getUrl(url);
    }
    @PostMapping("/download/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idRealtor")int id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Realtor realtor = realtorService.getById(id);
        if(realtor.getFiles() == null)realtor.setFiles(new ArrayList<>());
        String url = minioService.putImage(file);
        realtor.getFiles().add(url);
        realtorService.save(realtor);
        return ResponseEntity.ok(url);
    }
    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("url")String url, @RequestParam("idRealtor")int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteImg(url, "images");
        Realtor realtor = realtorService.getById(id);
        realtor.getFiles().remove(url);
        realtorService.save(realtor);
        return ResponseEntity.ok("deleteObj");
    }
    @GetMapping("/feedback/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id")int id) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        realtorFeedBackService.deleteById(id);
        return ResponseEntity.ok("deleteObj");
    }
    @GetMapping("/delete/contact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id")int id) {
        realtorContactService.deleteById(id);
        return ResponseEntity.ok("deleteObj");
    }
    @GetMapping("/feedback/save")
    public ResponseEntity<String> save(@RequestParam("id")int id, @RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("description")String description) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        realtorFeedBackService.save(new RealtorFeedBack(id, name, phone, description));
        return ResponseEntity.ok("saveObj");
    }
    @GetMapping("/download")
    @ResponseBody
    public byte[] downloadFile(@RequestParam String url) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return minioService.getPhoto(url, "images");
    }
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("realtorsActive", true);
    }
    @GetMapping("/for/select")
    @ResponseBody
    public Page<Realtor> search(@RequestParam("query") String name,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        Page<Realtor> searchResults = realtorService.forSelect(name, PageRequest.of(page, size, Sort.by(Sort.Order.asc("id"))));
        return searchResults;
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public Realtor getById(@PathVariable Integer id){
        try{
            return realtorService.getById(id);
        }catch (EntityNotFoundException e) {
            return null;
        }
    }
    @GetMapping("/getByCode/{code}")
    @ResponseBody
    public Realtor getByCode(@PathVariable Integer code){
        try{
            return realtorService.getByCode(code);
        }catch (EntityNotFoundException e) {
            return null;
        }
    }
}
