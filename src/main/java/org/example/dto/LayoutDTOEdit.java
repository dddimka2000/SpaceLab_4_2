package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class LayoutDTOEdit {
    @Size(min = 1, max = 31, message = "(Ру)Название планировок должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Ру)Название планировок должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameLayout;
    @Size(min = 1, max = 31, message = "(Англ)Название планировок должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Англ)Название планировок должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameLayoutEng;
    @Size(min = 1, max = 31, message = "(Укр)Название планировок должно быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Укр)Название планировок должно содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    private String nameLayoutUkr;

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
    @Size(min = 1, max = 701, message = "(Ру)Описания планировок должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Ру)Описания планировок должны содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionLayout;
    @Size(min = 1, max = 701, message = "(Англ)Описания планировок должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Англ)Описания планировок должны содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionLayoutEng;
    @Size(min = 1, max = 701, message = "(Укр)Описания планировок должно быть от 2 до 700 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "(Укр)Описания планировок должны содержать только буквы a-z A-Z, цифры 0-9, символы пробела и \"_\", \"-\", \"!\", \"?\", \",\"")
    private String descriptionLayoutUkr;

    private Optional<MultipartFile> img1Layout;
    private Optional<MultipartFile> img2Layout;
    private Optional<MultipartFile> img3Layout;
    private String img1Old;
    private String img2Old;
    private String img3Old;

}
