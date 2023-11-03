package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entity.property.type.PropertyBuildStatus;
import org.example.entity.property.type.PropertyObjectAddress;

import java.util.List;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Data
@Entity
@Table(name = "builder_objects", schema = "my_bd")
@Accessors(chain = true)
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

    //fixme enumerated
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

    // fixme why fetchType eager
    @OneToMany(mappedBy = "builderObject",fetch = FetchType.EAGER)
    private List<Layout> layouts;

}
