package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.property.type.ApplicationStatus;
import org.example.entity.property.type.PropertyApplicationType;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.entity.property.type.PropertyOrigin;

import java.util.List;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Entity
@Table(name = "applications")
@Data
public class BuyerApplication {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Embedded
    private PropertyObjectAddress address;

    private Integer roomQuantity;

    // fixme @Enumerated(value = EnumType.STRING) ?
    private ApplicationStatus status;
    private PropertyOrigin origin;
    private PropertyApplicationType type;

    private Integer floorMin, floorMax;
    private Integer priceMin, priceMax;
    private Integer plotAreaMin, plotAreaMax;
    private Integer houseAreaMin, houseAreaMax;

    @OneToOne
    private Buyer buyer;

    @ManyToOne
    private Realtor realtor;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObject builderObject;

    @OneToMany(mappedBy = "application")
    private List<BuyerApplicationEditLog> editHistory;

}
