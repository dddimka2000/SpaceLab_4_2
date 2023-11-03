package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.RealtorDto;
import org.example.dto.UserDto;
import org.example.entity.Realtor;
import org.example.entity.UserEntity;
import org.example.entity.UserReview;
import org.example.entity.UserRole;
import org.example.service.MinioService;
import org.example.service.ReviewServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

/*

fixme

use /admin/... for admin panel application
use /cabinet/... for realtor cabinet application

*/


@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final ReviewServiceImpl reviewService;
    private final MinioService minioService;

    /*
    fixme
    alternate approach in one line
    return new ModelAndView('banners/banners_table', 'banners', bannerService.findAll());
     */

    @GetMapping
    public ModelAndView mainPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("users/users_table");
        modelAndView.addObject("usersActive", true);
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView userInfo(@PathVariable("id")int id) {
        ModelAndView modelAndView = new ModelAndView("users/user_info");
        modelAndView.addObject("user", userService.getById(id));
        return modelAndView;
    }
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("users/user_add");
        modelAndView.addObject("userDto", new UserEntity());
        return modelAndView;
    }
    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> add(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if(bindingResult.hasErrors()) return ResponseEntity.ok().body(bindingResult.getAllErrors().toString());
        userService.add(userDto);
        return ResponseEntity.ok().body("Сохранено");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(){
        return new ModelAndView("users/user_add");
    }
    @GetMapping("/get/id/{id}")
    @ResponseBody
    public UserEntity getById(@PathVariable("id")int id){
        UserEntity user = userService.getById(id);
        return userService.getById(id);
    }
    @GetMapping("/get/url")
    @ResponseBody
    public ResponseEntity<String> getByUrl(@RequestParam String url){
        return ResponseEntity.ok().body(minioService.getUrl(url));
    }
    @GetMapping("/get-all")
    @ResponseBody
    public Page<UserEntity> getAll(@RequestParam("page")int page, @RequestParam("role")String role, @RequestParam("surname")String surname, @RequestParam("name")String name, @RequestParam("middleName")String middleName, @RequestParam("phone")String phone, @RequestParam("email")String email){
        return userService.getAll(page, role, surname, name, middleName, phone, email);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id")int id){
        userService.deleteById(id);
        return ResponseEntity.ok().body("Користува успішно видалено");
    }
    @PostMapping("/download/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idUser")int id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserEntity user = userService.getById(id);
        if(user.getFiles() == null)user.setFiles(new ArrayList<>());
        String url = minioService.putImage(file);
        user.getFiles().add(url);
        userService.save(user);
        return ResponseEntity.ok(url);
    }
    @GetMapping("/delete/file")
    public ResponseEntity<String> deleteFile(@RequestParam("userId")int id, @RequestParam("url")String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteImg(url, "images");
        UserEntity user = userService.getById(id);
        user.getFiles().remove(url);
        userService.save(user);
        return ResponseEntity.ok("Файл видалено успішно");
    }
    @GetMapping("/download")
    @ResponseBody
    public byte[] downloadFile(@RequestParam String url) throws MinioException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        return minioService.getPhoto(url, "images");
    }
    @GetMapping("/delete/review")
    public ResponseEntity<String> deleteFile(@RequestParam("id")int id){
        reviewService.deleteById(id);
        return ResponseEntity.ok("Відгук видалено успішно");
    }
    @PostMapping("/save/review")
    public ResponseEntity<String> saveReview(@ModelAttribute UserReview review){
        reviewService.save(review);
        return ResponseEntity.ok("Відгук видалено успішно");
    }

}
