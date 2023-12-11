package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String surname;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String name;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String middleName;
    @Size(min = 12, max = 15, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String phone;
    @NotNull(message = "{error.field.empty}")
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