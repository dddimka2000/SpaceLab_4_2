package org.example.dto;

import lombok.Data;
import org.example.entity.Realtor;
import org.example.entity.property.type.InformationSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class BuyerPersonalDataDto {
    private Integer id;
    private String surname;
    private String name;
    private String middleName;
    private String phone;
    private String email;
    private Realtor realtor;
    private String passport;
    private String comment;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    private InformationSource informationSource;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate lastContactDate;
    private List<MultipartFile> files;
}