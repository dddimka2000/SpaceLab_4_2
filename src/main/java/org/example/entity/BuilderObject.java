package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.entity.property.type.PropertyBuildStatus;
import org.example.entity.property.type.PropertyObjectAddress;

import java.util.List;

@Data
@Entity
@Table(name = "builder_objects", schema = "my_bd")
public class BuilderObject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Embedded
    private PropertyObjectAddress address;
    private String name;
    private Integer floorQuantity;
    private String phone;
    private String description_builder;
    private PropertyBuildStatus buildStatus;
    private String nameCompany;
    private String filePrices;
    private String fileInstallmentTerms;
    private String fileCheckerboard;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "name", column = @Column(name = "promotion_name")),
            @AttributeOverride( name = "description", column = @Column(name = "promotion_description")),
    })
    private BuilderObjectPromotion promotion;

    @OneToMany(mappedBy = "builderObject",fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Layout> layouts;

    @OneToMany(mappedBy = "builderObject", cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<BuyerApplication> buyerApplications;

}
