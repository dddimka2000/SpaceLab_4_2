package org.example.dto;

import lombok.Data;
import org.example.entity.property.StatusPotentialCustomer;

import java.time.LocalDate;

@Data
public class PotentialCustomerDtoForView {
    private Integer id;
    private String fullName;
    private String phone;
    private LocalDate viewDate;
    private LocalDate registrationDate;
    private StatusPotentialCustomer statusPotentialCustomer;
    private String realtorFullName;
    private String builderObject;
}