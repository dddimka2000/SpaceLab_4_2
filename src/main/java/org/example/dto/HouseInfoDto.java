package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.OwnershipType;
import org.example.entity.property.type.PlotPurposeType;
import org.example.entity.property.type.PropertyHouseType;
import org.example.entity.property.type.PublicationStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class HouseInfoDto {
    private Integer id;
    private PublicationStatus publicationStatus;
    private Integer objectCode, branchCode, codeStaff;
    private String landmark;
    private Integer price;
    private PropertyHouseType houseType;
    private Integer plotAreaTotal, plotAreaFree;
    private Boolean landOwnershipPresent;
    private PlotPurposeType plotPurposeType;
    private Integer houseQuantity, floorQuantity, roomQuantity, bedroomQuantity;
    private Integer roomMeters, areaTotal, areaLiving, areaKitchen;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate lastContactDate;
    private Boolean vnp;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate vnpExpirationDate;
    private String informationSource;
    private Boolean bargain, exclusive, urgent, free, open, intermediary;
    private String descriptionUk, advertisementHeadlineUk, advertisementTextUk;
    private String description, advertisementHeadline, advertisementText;
    private String descriptionEn, advertisementHeadlineEn, advertisementTextEn;
    private Boolean advertisementEnabled;
    private List<MultipartFile> files;
    private List<MultipartFile> pictures;


    private String ownerName;
    private String ownerPhone;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate boughtDate;
    private OwnershipType ownershipType;
    private String notes;
    private String cadastralNumber;
    private String plotDescription;
    private String administrationComment;
}