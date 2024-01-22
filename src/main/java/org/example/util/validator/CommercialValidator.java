package org.example.util.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.CommercialInfoDto;
import org.example.service.BranchServiceImpl;
import org.example.service.PropertyObjectServiceImpl;
import org.example.service.RealtorServiceImpl;
import org.example.service.ValidateServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
@RequiredArgsConstructor
public class CommercialValidator implements Validator {
    private final RealtorServiceImpl realtorService;
    private final BranchServiceImpl branchService;
    private final ValidateServiceImpl validateService;
    private final PropertyObjectServiceImpl propertyObjectService;
    @Override
    public boolean supports(Class<?> clazz) {
        return CommercialInfoDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommercialInfoDto commercialInfoDto = (CommercialInfoDto) target;
        try {
            realtorService.getByCode(commercialInfoDto.getStaffCode());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("staffCode", "", "The code for this employee does not exist");
        }

        try {
            branchService.getByCode(commercialInfoDto.getBranchCode());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("branchCode", "", "The branch with this code was not found");
        }

        if (commercialInfoDto.getId() == null && validateService.validPhoneNumber(commercialInfoDto.getOwnerPhone())) {
            errors.rejectValue("ownerPhone", "", "The phone already exists");
        } else if (validateService.validPhoneNumber(commercialInfoDto.getOwnerPhone(), commercialInfoDto.getId())) {
            errors.rejectValue("ownerPhone", "", "The phone already exists");
        }

        if (commercialInfoDto.getId() == null && propertyObjectService.existsByCode(commercialInfoDto.getObjectCode().toString())) {
            errors.rejectValue("objectCode", "", "The object with this code already exists");
        } else if (!propertyObjectService.getById(commercialInfoDto.getId()).getObjectCode().equals(commercialInfoDto.getObjectCode().toString())
                && propertyObjectService.existsByCode(commercialInfoDto.getObjectCode().toString())) {
            errors.rejectValue("objectCode", "", "The object with this code already exists");
        }
    }
}
