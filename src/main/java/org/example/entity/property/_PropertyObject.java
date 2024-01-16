package org.example.entity.property;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.BuilderObject;
import org.example.entity.Realtor;
import org.example.entity.UserEntity;
import org.example.entity.property.type.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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

    private Boolean status;

    private String objectCode, branchCode;
    private Integer price;
    private String landmark;

    @Embedded
    private PropertyObjectAddress address;      // область, город, район, топозона, улица, номер дома, секция

    private Integer floor, floorQuantity;
    private Integer roomQuantity;

    private String apartmentNumber;
    private String ownerName, ownerNameEng, ownerNameUkr;
    private String ownerPhone;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate boughtDate;

    private OwnershipType ownershipType;
    @Lob
    @Column(length = 3000)
    private String notes;

    private PropertyBuildStatus buildStatus;

//    @DateTimeFormat(pattern="yyyy/MM/dd")
//    private LocalDate buildFinishDate;

    private Integer buildFinishDate;
    private Integer areaTotal, areaLiving, areaKitchen;
    private String roomMeters;
    private Double heightCeiling;

    private String wallMaterial,wallMaterialEng, wallMaterialUkr;

    private PropertyOrigin propertyOrigin;   // состояние квартиры/интерьера: от строителей / от инвесторов

    private Boolean bargain, exclusive, urgent, free, open, intermediary;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate lastContactDate;

    private Boolean vnp;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate vnpExpirationDate;
    private String informationSource;

    @Lob
    @Column(length = 3000)
    private String descriptionUk, advertisementHeadlineUk, advertisementTextUk;
    @Lob
    @Column(length = 3000)
    private String description, advertisementHeadline, advertisementText;
    @Lob
    @Column(length = 3000)
    private String descriptionEn, advertisementHeadlineEn, advertisementTextEn;

    private Boolean advertisementEnabled;

    @Column(length = 3000)
    private String administrationComment;

    @ElementCollection
    private List<String> files;

    @ElementCollection
    private List<String> pictures;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BuilderObject builderObject;

    @ManyToOne
    @JoinColumn(name = "realtor_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Realtor realtor;
}
