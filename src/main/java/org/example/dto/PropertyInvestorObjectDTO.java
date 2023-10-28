package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@Data
public class PropertyInvestorObjectDTO {
    private Integer id;
    private Boolean status;
    private String objectCode;
    private String branchCode;
    private String employeeCode;
    private Integer price;
    private String landmark;
    private PropertyObjectAddress address;//
    private Integer floor;
    private Integer floorQuantity;
    private Integer roomQuantity;
    private String apartmentNumber;
    private String ownerName;
    private String ownerPhone;
    private LocalDate boughtDate;
    private OwnershipType ownershipType;
    private String notes;
    private PropertyBuildStatus buildStatus;
    private LocalDate buildFinishDate;
    private Integer areaTotal;
    private Integer areaLiving;
    private Integer areaKitchen;
    private String roomMeters;
    private Double heightCeiling;
    private String wallMaterial;
    private PropertyOrigin propertyOrigin;
    private Boolean top;
    private Boolean exclusive;
    private Boolean urgent;
    private Boolean free;
    private Boolean open;
    private Boolean intermediary;
    private LocalDate lastContactDate;
    private Boolean vnp;
    private LocalDate vnpExpirationDate;
    private String informationSource;
    private String description;
    private String advertisementHeadline;
    private String advertisementText;
    private Boolean advertisementEnabled;
    private String administrationComment;
    private List<String> files_name;
    private List<String> pictures_name;
    private List<MultipartFile> files;
    private List<MultipartFile> pictures;
    private RealtorDto realtor;
    private PublicationStatus publicationStatus;

}
