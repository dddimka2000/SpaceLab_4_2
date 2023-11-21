package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.entity.BuilderObject;
import org.example.entity.property.type.OwnershipType;
import org.example.entity.property.type.PropertyBuildStatus;
import org.example.entity.property.type.PropertyHouseType;
import org.example.entity.property.type.PublicationStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommercialInfoDto {
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String ownerName;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String ownerPhone;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate boughtDate;
//    @NotNull(message = "{error.field.empty}")
    private OwnershipType ownership;
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

    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    private Integer id;
    @NotNull(message = "{error.field.empty}")
    private PublicationStatus publicationStatus;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 999999999, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer objectCode, branchCode, staffCode;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String landmark;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 214748364, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer price;
    @NotNull(message = "{error.field.empty}")
    private BuilderObject builderObject;
    @NotNull(message = "{error.field.empty}")
    private PropertyBuildStatus buildStatus;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateDeliveryHouse;
    @Min(value = 1, message = "{error.field.min-value}")
    @Max(value = 1000000, message ="{error.field.max-value}")
    @NotNull(message = "{error.field.empty}")
    private Integer floor, floorQuantity, roomQuantity;
    @NotNull(message = "{error.field.empty}")
    private PropertyHouseType houseType;
    @NotNull(message = "{error.field.empty}")
    private Boolean vnp;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    @NotNull(message = "{error.field.empty}")
    private LocalDate vnpExpirationDate;
    @Size(min = 1, max = 999, message = "{error.field.size}")
    @NotNull(message = "{error.field.empty}")
    private String informationSource;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(message = "{error.field.empty}")
    private LocalDate lastContactDate;
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
}