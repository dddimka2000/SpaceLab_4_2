package org.example.util.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseInfoDto;
import org.example.service.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class HouseValidator implements Validator {
    private final RealtorServiceImpl realtorService;
    private final BranchServiceImpl branchService;
    private final ValidateServiceImpl validateService;
    private final PropertyObjectServiceImpl propertyObjectService;

    @Override
    public boolean supports(Class<?> clazz) {
        return HouseInfoDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HouseInfoDto houseInfoDto = (HouseInfoDto) target;
        try {
            realtorService.getByCode(houseInfoDto.getCodeStaff());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("codeStaff", "", "The code for this employee does not exist");
        }

        try {
            branchService.getByCode(houseInfoDto.getBranchCode());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("branchCode", "", "The branch with this code was not found");
        }

        if (houseInfoDto.getId() == null && validateService.validPhoneNumber(houseInfoDto.getOwnerPhone())) {
            errors.rejectValue("ownerPhone", "", "The phone already exists");
        } else if (validateService.validPhoneNumber(houseInfoDto.getOwnerPhone(), houseInfoDto.getId())) {
            errors.rejectValue("ownerPhone", "", "The phone already exists");
        }

        if (houseInfoDto.getId() == null && propertyObjectService.existsByCode(houseInfoDto.getObjectCode().toString())) {
            errors.rejectValue("objectCode", "", "The object with this code already exists");
        } else if (!propertyObjectService.getById(houseInfoDto.getId()).getObjectCode().equals(houseInfoDto.getObjectCode().toString()) && propertyObjectService.existsByCode(houseInfoDto.getObjectCode().toString())) {
            errors.rejectValue("objectCode", "", "The object with this code already exists");
        }
    }
}