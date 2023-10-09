package org.example.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "object", schema = "my_bd", catalog = "")
public class ObjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "code")
    private String code;
    @Column(name = "photo_check")
    private Boolean photoCheck;
    @Column(name = "comment")
    private String comment;
    @Column(name = "notes")
    private String notes;
    @Column(name = "topozone")
    private String topozone;
    @Column(name = "street")
    private String street;
    @Column(name = "advertising")
    private Boolean advertising;
    @Column(name = "quantity_rooms")
    private Integer quantityRooms;
    @Column(name = "status")
    private String status;
    @Column(name = "floor")
    private String floor;
    @Column(name = "quantity_floors")
    private String quantityFloors;
    @Column(name = "price")
    private String price;
    @Column(name = "area")
    private String area;
    @Column(name = "date_of_purchase")
    private Timestamp dateOfPurchase;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "ownership")
    private String ownership;
    @Column(name = "living_area")
    private Integer livingArea;
    @Column(name = "total_area")
    private Integer totalArea;
    @Column(name = "kitchen_area")
    private Integer kitchenArea;
    @Column(name = "footage_of_rooms")
    private String footageOfRooms;
    @Column(name = "ceiling_height")
    private Integer ceilingHeight;
    @Column(name = "wall_material")
    private String wallMaterial;
    @Column(name = "condition_of_the_apartment")
    private String conditionOfTheApartment;
    @Column(name = "kitchen")
    private String kitchen;
    @Column(name = "bathroom")
    private String bathroom;
    @Column(name = "balcony_loggia")
    private String balconyLoggia;
    @Column(name = "view_from_the_window")
    private String viewFromTheWindow;
    @Column(name = "kitchen_stove")
    private String kitchenStove;
    @Column(name = "heating")
    private String heating;
    @Column(name = "date_of_last_contact")
    private Timestamp dateOfLastContact;
    @Column(name = "description")
    private String description;
    @Column(name = "advertising_headline")
    private String advertisingHeadline;
    @Column(name = "advertising_status")
    private Byte advertisingStatus;
    @Column(name = "advertising_text")
    private String advertisingText;
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "employee_code")
    private String employeeCode;
    @OneToMany(mappedBy = "objectByIdObject")
    private Collection<ApplicationEntity> applicationsById;
    @OneToMany(mappedBy = "objectByIdObject")
    private Collection<ImgObjectEntity> imgObjectsById;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity userByIdUser;
    @OneToMany(mappedBy = "objectByIdObject")
    private Collection<OfferToChangeEntity> offerToChangesById;

}
