package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl {
    private final RealtorContactServiceImpl contactService;
    private final BranchServiceImpl branchService;
    private final PropertyObjectServiceImpl objectService;
    private final BuyerServiceImpl buyerService;
    public boolean validPhoneNumber(String phone){
        boolean result = false;
        if(contactService.existsByPhone(phone))result = true;
        if(branchService.existsByPhone(phone))result = true;
        if(objectService.existsByPhone(phone))result = true;
        if(buyerService.existsByPhone(phone))result = true;
        return result;
    }
    public boolean validPhoneNumber(String phone, int id){
        boolean result = false;
        if(contactService.existsByPhone(phone))result = true;
        if(branchService.existsByPhone(phone))result = true;
        if(objectService.existsByPhone(phone) && objectService.getByPhone(phone).getId() != id)result = true;
        if(buyerService.existsByPhone(phone))result = true;
        return result;
    }
}