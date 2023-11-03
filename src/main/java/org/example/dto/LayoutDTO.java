package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/*

fixme

use key-values from message.properties in annotations, like this:

@NotBlank(message = {error.field.empty})
@Size(max = 50, message = {error.field.max-size})

in message.properties:
error.field.empty = "Заповніть поле!"
error.field.max-size = "Поле повинно мати не більше {max} символів"!

 */

@Data
public class LayoutDTO {

    // fixme statusLayout == null ?

    @Size(min = 1, max = 31, message = "Название планировок должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название планировок должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameLayout;
    @NotNull(message = "Заполните цену в планировке")
    @Min(value = 1, message = "Введите корректную цену для планировок")
    @Max(value = 100000001, message = "Введите корректную площадь для планировок")
    private Integer priceLayout;
    @NotNull(message = "Заполните кол-во комнат в планировке")
    @Min(value = 1, message = "Введите корректное кол-во комнат")
    @Max(value = 1001, message = "Введите корректное кол-во комнат")
    private Integer roomQuantityLayout;
    @NotNull(message = "Заполните площадь в планировке")
    @Min(value = 1, message = "Введите корректную площадь для планировок")
    @Max(value = 100001, message = "Введите корректную площадь для планировок")
    private Integer areaLivingLayout, areaTotalLayout, areaKitchenLayout;
    private Boolean statusLayout;
    @Size(min = 1, max = 701, message = "Описания планировок должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Описания планировок должны содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionLayout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img1Layout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img2Layout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img3Layout;
}
