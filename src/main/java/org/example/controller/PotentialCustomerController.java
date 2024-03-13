package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PotentialCustomerDtoForView;
import org.example.entity.PotentialCustomer;
import org.example.entity.property.StatusPotentialCustomer;
import org.example.service.PotentialCustomerServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/potential_customer")
public class PotentialCustomerController {
    private final PotentialCustomerServiceImpl potentialCustomerService;
    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("potentialCustomers", true);
    }
    @GetMapping
    public ModelAndView getAll(){
        return new ModelAndView("potential_customer/potential_customer_table");
    }
    @GetMapping("/get-all")
    @ResponseBody
    public Page<PotentialCustomerDtoForView> getAll(@RequestParam Integer page, @RequestParam Integer numberOfElement, @RequestParam String search){
        Page<PotentialCustomerDtoForView> result = potentialCustomerService.getAll(page, numberOfElement, search);
        return result;
    }
    @PutMapping("/change/status")
    @ResponseBody
    public ResponseEntity<String> changeStatus(@RequestParam Integer id, @RequestParam StatusPotentialCustomer status){
        potentialCustomerService.changeStatus(id, status);
        return ResponseEntity.ok("changed");
    }
}