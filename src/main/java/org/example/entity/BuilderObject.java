package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.util.property.PropertyBuildStatus;
import org.example.util.property.PropertyObjectAddress;

import java.util.List;

@Data
@Entity
@Table(name = "builder_objects", schema = "my_bd", catalog = "")
public class BuilderObject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Embedded
    private PropertyObjectAddress address;

    private Integer floorQuantity;
    private Integer priceFrom;

    private String phone;

    private String description_builder;

    private PropertyBuildStatus buildStatus;

    private String nameCompany;
    private String filePrices;
    private String fileInstallmentTerms;
    private String fileCheckerboard;

    @Embedded
    private BuilderObjectPromotion promotion;

    @OneToMany(mappedBy = "builderObject")
    private List<Layout> layoutsById;

}
