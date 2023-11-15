package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.entity.Branch;
import org.example.entity.RealtorContact;
import org.example.entity.RealtorFeedBack;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class RealtorDto {
    private Integer id;
    private String password;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 100000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer code;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String name;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String surname;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String middleName;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String nameUK;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String surnameUK;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String middleNameUK;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String nameEN;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String surnameEN;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String middleNameEN;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Некоректний email")
    private String email;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "{error.field.empty}")
    private LocalDate birthdate;
    private MultipartFile img;
    private Branch branch;
    private List<RealtorContact> contacts;
    private List<MultipartFile> files;
    private List<RealtorFeedBack> realtorFeedBacks;
}