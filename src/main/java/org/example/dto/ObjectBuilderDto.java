package org.example.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.entity.Layout;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Data
public class ObjectBuilderDto {
    @Size(min = 1, max = 31, message = "Название должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameObject;
    @Size(min = 1, max = 31, message = "Название области должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название области должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String region;
    @Size(min = 1, max = 31, message = "Название улицы должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название улицы должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String street;
    @Size(min = 1, max = 31, message = "Название района должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название района должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String district;
    @Size(min = 1, max = 31, message = "Город должен быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название города должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String city;
    @Size(min = 1, max = 31, message = "Топозона должна быть от 2 до 30 символов.")
    private String topozone;
    @NotNull(message = "Заполните номер дома")
    @Min(value = 0, message = "Введите корректный номер дома")
    @Max(value = 2001, message = "Введите корректный номер дома")
    private Integer houseNumber;
    @NotNull(message = "Заполните номер секции")
    @Min(value = 0, message = "Введите корректный номер секции")
    @Max(value = 2001, message = "Введите корректный номер секции")
    private Integer section;
    @NotNull(message = "Заполните этажность")
    @Min(value = 0, message = "Введите корректный номер этажа")
    @Max(value = 201, message = "Введите корректный номер этажа")
    private Integer floorQuantity;
    @Size(min = 1, max = 31, message = "Название компании должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название компании должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameCompany;
    @Size(min = 1, max = 31, message = "Статус должен быть от 2 до 30 символов.")
    private String buildStatus;
    @Size(min = 10, max = 15, message ="Номер должен быть от 11 до 15 символов.")
    @Pattern(regexp = "\\+?[0-9]+", message = "Номер телефона должен начинаться с '+' и содержать только цифры.")
    private String telephone;

    @Size(min = 1, max = 31, message = "Название акции быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название акции должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String promotionName;
    @NotNull(message = "Файлы не могут быть пусты")
    private MultipartFile chessboardFile;
    @NotNull(message = "Файлы не могут быть пусты")
    private MultipartFile installmentTerms;
    @NotNull(message = "Файлы не могут быть пусты")
    private MultipartFile prices;

    @Size(min = 1, max = 701, message = "Описание должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9_-]+$", message = "Описание должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String description;
    @Size(min = 1, max = 701, message = "Описание должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Описание должно содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionPromotion;
    @Size(min = 1, max = 701, message = "Описание должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Описание акции должно содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String statusPromotion;


    @NotEmpty(message = "Заполните галлерею")
    private List<MultipartFile> files;

    @Valid
    @Embedded
    @NotEmpty(message = "Заполните планировку, минимум 1 карточку")
    private List<LayoutDTO> layoutDTOList;
}

