package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LayoutDTOEdit {
    private String nameLayout;
    private Integer priceLayout;
    private Integer roomQuantityLayout;
    private Integer areaLivingLayout, areaTotalLayout, areaKitchenLayout;
    private Boolean statusLayout;
    private String descriptionLayout;
    private MultipartFile img1Layout;
    private MultipartFile img2Layout;
    private MultipartFile img3Layout;
    private String img1Old;
    private String img2Old;
    private String img3Old;

}
