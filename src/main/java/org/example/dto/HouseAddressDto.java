package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.OwnershipType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class HouseAddressDto {
    private Integer houseNumber;
    private String region;
    private String city;
    private String district;
    private String street;
    private String zone;

}
