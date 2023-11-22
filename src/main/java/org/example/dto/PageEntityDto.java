package org.example.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PageEntityDto {
    private Integer id;
    @Size(min = 1, max = 31, message = "Название должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String name;
    @Size(min = 1, max = 31, message = "Название должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameUkr;
    @Size(min = 1, max = 31, message = "Название должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameEng;
    @Size(min = 1, max = 10000, message = "(Ру)Описание должно иметь хотя бы 1 символ.")
    private String content;
    @Size(min = 1, max = 10000, message = "(Укр)Описание должно иметь хотя бы 1 символ.")
    private String contentUkr;
    @Size(min = 1, max = 10000, message = "(Англ)Описание должно иметь хотя бы 1 символ.")
    private String contentEng;
}
