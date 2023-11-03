package org.example.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
public class PageEntityDto {
    private Integer id;
    @Size(min = 1, max = 31, message = "Название должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String name;
    @Size(min = 1, max = 10000, message = "Описание должно иметь хотя бы 1 символ.")
    private String content;
}
