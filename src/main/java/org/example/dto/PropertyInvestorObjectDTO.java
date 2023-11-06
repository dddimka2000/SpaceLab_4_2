package org.example.dto;

import lombok.Data;
import org.example.entity.property.type.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class PropertyInvestorObjectDTO {
    public Integer id;
    public Boolean status; //
    public String objectCode; //
    public Integer branchCode;//
    public Integer employeeCode;//
    public Integer price; //
    public String landmark;//
    public PropertyObjectAddress address;//
    public String referencePoint;//
    public Integer residentialComplexId;
    public Integer floor;//
    public Integer floorQuantity;//
    public Integer roomQuantity;//
    public String apartmentNumber;//
    public String ownerName;//
    public String ownerPhone;//
    public LocalDate boughtDate;//
    public OwnershipType ownershipType;//
    public String notes;
    public PropertyBuildStatus buildStatus;//
    public LocalDate buildFinishDate;
    public Integer areaTotal;//
    public Integer areaLiving;//
    public Integer areaKitchen;//
    public KitchenType kitchenType;//
    public BathroomType bathroomType;//
    public BalconyType balconyType;//
    public WindowViewType windowViewType;//
    public KitchenStoveType kitchenStoveType;//
    public HeatingType heatingType;//
    public String roomMeters;//
    public Double heightCeiling;//
    public String wallMaterial;//
    public PropertyOrigin propertyOrigin;//
    public Boolean bargain;
    public Boolean exclusive;
    public Boolean urgent;
    public Boolean free;
    public Boolean open;
    public Boolean intermediary;
    public LocalDate lastContactDate;//
    public Boolean vnp;//
    public LocalDate vnpExpirationDate;//
    public String informationSource;//
    public String description;
    public String advertisementHeadline;
    public String advertisementText;
    public Boolean advertisementEnabled;
    public String administrationComment;//
    public List<String> filesName;
    public List<String> picturesName;
    public List<MultipartFile> files;
    public List<MultipartFile> pictures;
    public RealtorDto realtor;
    public PublicationStatus publicationStatus;

}
