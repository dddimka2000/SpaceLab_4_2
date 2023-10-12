package org.example.entity.property;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.BuilderObject;
import org.example.entity.UserEntity;
import org.example.util.property.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class _PropertyObject {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    private PublicationStatus status;

    private String objectCode, branchCode, employeeCode;
    private Integer price;
    private String landmark;

    @Embedded
    private PropertyObjectAddress address;      // область, город, район, топозона, улица, номер дома, секция

    private Integer floor, floorQuantity;
    private Integer roomQuantity;

    private String apartmentNumber;
    private String ownerName;
    private String ownerPhone;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate boughtDate;

    private OwnershipType ownershipType;
    private String notes;

    private PropertyBuildStatus buildStatus;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate buildFinishDate;

    private Integer areaTotal, areaLiving, areaKitchen;
    private String roomMeters;
    private Double heightCeiling;

    private String wallMaterial;

    private PropertyOrigin propertyOrigin;   // состояние квартиры/интерьера: от строителей / от инвесторов

    private Boolean top, exclusive, urgent, free, open, intermediary;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate lastContactDate;

    private Boolean vnp;                    // что такое ВНП блять

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate vnpExpirationDate;
    private String informationSource;

    private String description, advertisementHeadline, advertisementText;
    private Boolean advertisementEnabled;

    private String administrationComment;

    @ElementCollection
    private List<String> files;

    @ElementCollection
    private List<String> pictures;

    @ManyToOne
    private BuilderObject builderObject;

    @ManyToOne
    private UserEntity realtor;
}
