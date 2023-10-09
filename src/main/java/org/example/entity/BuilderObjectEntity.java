package org.example.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "builder_object", schema = "my_bd", catalog = "")
public class BuilderObjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "district")
    private String district;
    @Column(name = "topozone")
    private String topozone;
    @Column(name = "street")
    private String street;
    @Column(name = "quantity_floors")
    private Integer quantityFloors;
    @Column(name = "price_from")
    private Integer priceFrom;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "city")
    private String city;
    @Column(name = "region")
    private String region;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private String deadline;
    @Column(name = "name_company")
    private String nameCompany;
    @Column(name = "file_prices")
    private String filePrices;
    @Column(name = "file_installment_terms")
    private String fileInstallmentTerms;
    @Column(name = "file_checkerboard")
    private String fileCheckerboard;
    @Column(name = "number")
    private Integer number;
    @Column(name = "section")
    private String section;
    @Column(name = "builder_company")
    private String builderCompany;
    @OneToMany(mappedBy = "builderObjectByIdBuilderObject")
    private List<ApplicationEntity> applicationsById;
    @OneToMany(mappedBy = "builderObjectByIdBuildObject")
    private List<ImgBuilderObjectEntity> imgBuilderObjectsById;
    @OneToMany(mappedBy = "builderObjectByIdBuilderObject")
    private List<LayoutEntity> layoutsById;
    @OneToMany(mappedBy = "builderObjectByIdBuilderObject")
    private List<PromotionEntity> promotionsById;

}
