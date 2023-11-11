package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.PlotPurposeType;
import org.example.entity.property.type.PropertyHouseType;
import org.example.entity.property.type.PublicationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class HouseInfoDto {
    private PublicationStatus publicationStatus;
    private Integer codeObject, codeBranch, codeStaff;
    private String landmark;
    private Integer price;
    private PropertyHouseType propertyHouseType; //propertyType
    private Integer plotAreaTotal, plotAreaFree;
    private Boolean landOwnershipPresent;
    private PlotPurposeType plotPurposeType;
    private Integer houseQuantity, floorQuantity, roomQuantity, bedroomQuantity;
    private Integer roomMeters, areaTotal, areaLiving, areaKitchen;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate lastContactDate;//nemaye
    private Boolean vnp;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate vnpExpirationDate;
    private String informationSource;
    private Boolean bargain, exclusive, urgent, free, open, intermediary;
    //tri nastupniy udalit
    private String descriptionUk, advertisementHeadlineUk, advertisementTextUk;
    private String descriptionRu, advertisementHeadlineRu, advertisementTextRu;
    private String descriptionEn, advertisementHeadlineEn, advertisementTextEn;
    private Boolean advertisementEnabled;
}