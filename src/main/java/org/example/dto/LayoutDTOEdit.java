package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class LayoutDTOEdit {
    @Size(min = 1, max = 31, message = "Название планировок должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Название планировок должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameLayout;
    @Min(value = 1, message = "Введите корректную цену для планировок")
    @Max(value = 100000001, message = "Введите корректную площадь для планировок")
    private Integer priceLayout;
    @Min(value = 1, message = "Введите корректное кол-во комнат")
    @Max(value = 1001, message = "Введите корректное кол-во комнат")
    private Integer roomQuantityLayout;
    @Min(value = 1, message = "Введите корректную площадь для планировок")
    @Max(value = 100001, message = "Введите корректную площадь для планировок")
    private Integer areaLivingLayout, areaTotalLayout, areaKitchenLayout;
    private Boolean statusLayout;
    @Size(min = 1, max = 701, message = "Описания планировок должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Описания планировок должны содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionLayout;

    private Optional<MultipartFile> img1Layout;
    private Optional<MultipartFile> img2Layout;
    private Optional<MultipartFile> img3Layout;
    private String img1Old;
    private String img2Old;
    private String img3Old;

}
