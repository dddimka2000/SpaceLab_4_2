package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BranchDto {
    private Integer id;

    @NotNull(message = "Поле повинно бути заповненим")
    @Min(value = 1, message = "Число повинно бути більшим за 1")
    @Max(value = 99999999, message = "Число повинно бути меншим за 99999999")
    private Integer code;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String name;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String address;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Телефон повинен бути у форматі +380xxxxxxxxx")
    private String telephone;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Некоректний email")
    private String email;
    private MultipartFile imgPath;
}
