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

/*

fixme

use key-values from message.properties in annotations, like this:

@NotBlank(message = {error.field.empty})
@Size(max = 50, message = {error.field.max-size})

in message.properties:
error.field.empty = "Заповніть поле!"
error.field.max-size = "Поле повинно мати не більше {max} символів"!

DO NOT USE OPTIONALS HERE

maybe split these DTO into multiple smaller ones , like AddressDTO or smth

 */

@Data
public class RealtorDto {
    private Integer id;
    @Size(min = 8, max = 50, message = "Поле повино містити від 8 до 50 символів")
    private String password;
    @NotNull(message = "Поле повинно бути заповненим")
    @Min(value = 1, message = "Число повинно бути більшим за 1")
    @Max(value = 99999999, message = "Число повинно бути меншим за 99999999")
    private Integer code;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String name;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String surname;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String middleName;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Некоректний email")
    private String email;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    private MultipartFile img;
    private Branch branch;
    private List<RealtorContact> contacts;
    private List<MultipartFile> files;
    private List<RealtorFeedBack> realtorFeedBacks;
}