package org.example.entity.property.type;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Embeddable
public class PropertyObjectAddress {
    @Size(min = 1, max = 999, message = "Регион должен быть от 2 до 999 символов.")
    @Pattern(regexp = "^[ а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Регион должен содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String region;
    @Size(min = 1, max = 999, message = "Регион должен быть от 2 до 999 символов.")
    @Pattern(regexp = "^[ а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Регион должен содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String city;
    @Size(min = 1, max = 999, message = "Регион должен быть от 2 до 999 символов.")
    @Pattern(regexp = "^[ а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Регион должен содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String district;
    @Size(min = 1, max = 999, message = "Топозона должна быть от 2 до 999 символов.")
    @Pattern(regexp = "^[ а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Топозона должна содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String zone;
    @Size(min = 1, max = 31, message = "(Ру)Название улицы должно быть от 2 до 30 символов.")
    private String street;
    @Size(min = 1, max = 31, message = "(Укр) Название улицы должно быть от 2 до 30 символов.")
    private String streetUkr;
    @Size(min = 1, max = 31, message = "(Англ) Название улицы должно быть от 2 до 30 символов.")
    private String streetEng;
    @NotNull(message = "Введите номер дома")
    @Min(value = 1, message = "Введите корректный номер дома")
    @Max(value = 100000001, message = "Введите корректный номер дома")
    private Integer houseNumber;
    @NotNull(message = "Введите секцию дома")
    @Min(value = 1, message = "Введите корректную секцию дома")
    @Max(value = 100000001, message = "Введите корректную секцию дома")
    private Integer section;
}
