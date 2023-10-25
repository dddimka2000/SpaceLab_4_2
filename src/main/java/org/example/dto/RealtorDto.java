package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.entity.Branch;
import org.example.entity.RealtorContact;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class RealtorDto {
    private Integer id;
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
    private LocalDate birthdate;
    private MultipartFile img;
    private Branch branch;
    private List<RealtorContact> contacts;
}