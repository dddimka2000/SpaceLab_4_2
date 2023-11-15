package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer id;
    @NotNull(message = "{error.field.empty}")
    private PublicationStatus publicationStatus;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 999999999, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer objectCode, branchCode, codeStaff;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String landmark;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer price;
    @NotNull(message = "{error.field.empty}")
    private PropertyHouseType houseType;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer plotAreaTotal, plotAreaFree;
    @NotNull(message = "{error.field.empty}")
    private Boolean landOwnershipPresent;
    @NotNull(message = "{error.field.empty}")
    private PlotPurposeType plotPurposeType;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer houseQuantity, floorQuantity, roomQuantity, bedroomQuantity;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer roomMeters, areaTotal, areaLiving, areaKitchen;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate lastContactDate;
    @NotNull(message = "{error.field.empty}")
    private Boolean vnp;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    @NotNull(message = "{error.field.empty}")
    private LocalDate vnpExpirationDate;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String informationSource;
    @NotNull(message = "{error.field.empty}")
    private Boolean bargain, exclusive, urgent, free, open, intermediary;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String descriptionUk, advertisementHeadlineUk, advertisementTextUk;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String description, advertisementHeadline, advertisementText;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String descriptionEn, advertisementHeadlineEn, advertisementTextEn;
    @NotNull(message = "{error.field.empty}")
    private Boolean advertisementEnabled;
    private List<MultipartFile> files;
    private List<MultipartFile> pictures;


    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String ownerName;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String ownerPhone;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate boughtDate;
    @NotNull(message = "{error.field.empty}")
    private OwnershipType ownershipType;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String notes;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String cadastralNumber;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String plotDescription;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String administrationComment;
}