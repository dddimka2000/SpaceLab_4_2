package org.example.dto;

import lombok.Data;

@Data
public class LayoutDTO {
    private String name;
    private Integer price;
    private Integer roomQuantity;
    private Integer areaLiving, areaTotal, areaKitchen;
    private Boolean status;
    private String description;
    private String img1;
    private String img2;
    private String img3;
}
