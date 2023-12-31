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
@Table(name = "builder_objects")
public class BuilderObject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Embedded
    private PropertyObjectAddress address;
    private String name, nameEnglish, nameUkraine;
    private Integer floorQuantity;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String description_builder, description_builderEng, description_builderUkr;
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

    @OneToMany(mappedBy = "builderObject",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Layout> layouts;

    @OneToMany(mappedBy = "builderObject", cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<BuyerApplication> buyerApplications;

}
