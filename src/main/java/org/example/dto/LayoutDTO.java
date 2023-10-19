package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LayoutDTO {
    private String nameLayout;
    private Integer priceLayout;
    private Integer roomQuantityLayout;
    private Integer areaLivingLayout, areaTotalLayout, areaKitchenLayout;
    private Boolean statusLayout;
    private String descriptionLayout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img1Layout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img2Layout;
    @NotNull(message = "Фотографии в планировке не могут быть пусты")
    private MultipartFile img3Layout;
}
