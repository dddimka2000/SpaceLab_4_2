package org.example.dto;

import jakarta.annotation.Nullable;
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
public class BannerSlideDto {
    private Integer id;
    @NotBlank(message = "Поле с именем слайда должно быть заполнено")
    @Size(max = 50, message = "Поле с именем слайда может иметь максимум 50 символов")
    private String name;
    @NotNull(message = "Заполните поле очередь")
    @Min(value = 1, message = "Очередь может начинаться только с цифры 1")
    @Max(value = 1001, message = "Введите корректное число для поля очередь")
    private Integer queue;
    private MultipartFile imgPath;
    private String oldImgPath;
    private String oldImgName;

}
