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

@Data
public class BannerDto {
    private Integer id;
    @NotBlank(message = "Поле с именем баннера должно быть заполнено")
    @Size(max = 50, message = "Поле с именем баннера может иметь максимум 50 символов")
    private String nameBanners;
    private Boolean status;
    @Valid
    @NotEmpty(message = "Заполните минимум 1 слайд")
    private List<BannerSlideDto> slides;

}
