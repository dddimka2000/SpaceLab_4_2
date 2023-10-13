package org.example.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class ObjectBuilderDto {
    private String nameObject;
    private String region;
    private String street;
    private String district;
    private String city;
    private String topozone;
    private Integer houseNumber;
    private Integer section;
    private Integer floorQuantity;
    private String nameCompany;
    private String buildStatus;
    private String telephone;
    private Optional<MultipartFile> chessboardFile;
    private Optional<MultipartFile> installmentTerms;
    private Optional<MultipartFile> prices;
    private String description;
    private String descriptionPromotion;
    private String statusPromotion;

}

