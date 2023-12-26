package org.example.controller;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.*;
import org.example.entity.property.type.TypeObjectForUrl;
import org.example.service.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
    private final BuyerServiceImpl buyerService;
    private final MinioService minioService;
    private final HousesServiceImpl housesService;
    private final PropertyInvestorObjectService propertyInvestorObjectService;
    private final CommercialServiceImpl commercialService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("buyer/buyer_table");
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("buyer/buyer_add");
    }

    @GetMapping("/{id}")
    public ModelAndView info() {
        return new ModelAndView("buyer/buyer_info");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit() {
        return new ModelAndView("buyer/buyer_add");
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public Buyer getById(@PathVariable("id") Integer id) {
        Buyer buyer = buyerService.getById(id);
        return buyerService.getById(id);
    }

    @PostMapping("/add")
    @ResponseBody
    public Integer add(@ModelAttribute BuyerPersonalDataDto buyerPersonalDataDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return buyerService.addPersonalData(buyerPersonalDataDto);
    }

    @PostMapping("/add/application")
    public ResponseEntity<String> addApplication(@ModelAttribute BuyerApplication buyerApplication) {
        buyerService.addApplication(buyerApplication);
        return ResponseEntity.ok().body("saveObj");
    }

    @PostMapping("/add/note")
    public ResponseEntity<String> addNote(@ModelAttribute BuyerNote buyerNote) {
        buyerService.addNote(buyerNote);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/delete/note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Integer id) {
        buyerService.deleteNote(id);
        return ResponseEntity.ok().body("deleteObj");
    }

    @GetMapping("/delete/file")
    public ResponseEntity<String> deleteFile(@RequestParam("id") int id, @RequestParam("url") String url) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteImg(url, "images");
        Buyer buyer = buyerService.getById(id);
        buyer.getFiles().remove(url);
        buyerService.save(buyer);
        return ResponseEntity.ok("deleteObj");
    }

    @GetMapping("/get/application/history/{id}")
    @ResponseBody
    public List<BuyerApplicationEditLog> getHistory(@PathVariable Integer id) {
        return buyerService.getById(id).getApplication().getEditHistory();
    }

    @GetMapping("/get-all")
    @ResponseBody
    public Page<Buyer> getAll(@RequestParam Integer page, @RequestParam String branch, @RequestParam String realtor, @RequestParam String name, @RequestParam String phone, @RequestParam String email, @RequestParam String price) {
        return buyerService.getAll(page, branch, realtor, name, phone, email, price);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        buyerService.deleteById(id);
        return ResponseEntity.ok().body("deleteObj");
    }

    @GetMapping("/{typeObjectForUrl}/{id}")
    public ModelAndView filterObject(@PathVariable TypeObjectForUrl typeObjectForUrl, @PathVariable Integer id) {
        switch (typeObjectForUrl) {
            case HOUSE -> {
                return new ModelAndView("buyer/buyer_table", "filterObject", housesService.getById(id));
            }
            case INVESTOR -> {
                return new ModelAndView("buyer/buyer_table", "filterObject", propertyInvestorObjectService.findById(id));
            }
            case COMMERCIAL -> {
                return new ModelAndView("buyer/buyer_table", "filterObject", commercialService.getById(id));
            }
        }
        return new ModelAndView("buyer/buyer_table");
    }

    @GetMapping("/filter/forObject")
    @ResponseBody
    public List<Buyer> filterForObject(@RequestParam Integer id, @RequestParam String type) {
        return buyerService.filterForObject(id, type);
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("buyersActive", true);
    }
}
