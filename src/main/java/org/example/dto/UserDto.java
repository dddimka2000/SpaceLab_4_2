package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.entity.Branch;
import org.example.entity.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
public class UserDto {
    private Integer id;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Некоректний email")
    private String email;
    @Size(min = 8, max = 50, message = "Поле повино містити від 8 до 50 символів")
    private String password;
    private String confirmPassword;
    @NotNull(message = "Ролі повині бути вибрані")
    private Set<UserRole> roles;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String name;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String surname;
    @NotBlank(message = "Поле повинно бути заповненим")
    @Size(max = 50, message = "Поле може містити максимум 50 символів")
    private String middleName;
    @Pattern(regexp = "^\\+380\\d{9}$", message = "Некоректний номер")
    private String phone;
    private MultipartFile img;
    @NotNull(message = "Повинен бути вибраний хоть один філіал")
    private List<Branch> branches;
}
