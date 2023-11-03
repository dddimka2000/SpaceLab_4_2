package org.example.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.entity.BannerSlide;

import java.util.List;

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
public class BannerDto {
    private Integer id;
    @NotBlank(message = "Поле с именем баннера должно быть заполнено")
    @Size(max = 50, message = "Поле с именем баннера может иметь максимум 50 символов")
    private String nameBanners;
    private Boolean status;

    //fixme
    //List < @Valid BannerSlideDto > ?
    @Valid
    @NotEmpty(message = "Заполните минимум 1 слайд")
    private List<BannerSlideDto> slides;

}
