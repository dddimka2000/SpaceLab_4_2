package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.property.type.ApplicationStatus;
import org.example.entity.property.type.PropertyApplicationType;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.entity.property.type.PropertyOrigin;

import java.util.List;

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

    private ApplicationStatus status;
    private PropertyOrigin origin;
    private PropertyApplicationType type;

    private Integer floorMin, floorMax;
    private Integer priceMin, priceMax;
    private Integer plotAreaMin, plotAreaMax;
    private Integer houseAreaMin, houseAreaMax;
    @ManyToMany
    @ToString.Exclude
    private List<DistrictEntity> districts;
    @ManyToMany
    private List<TopozoneEntity> topzones;
    private String comment;
    @OneToOne
    @JsonBackReference
    @ToString.Exclude
    private Buyer buyer;
    @ManyToOne
    private Realtor realtor;
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    @ToString.Exclude
    private BuilderObject builderObject;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<BuyerApplicationEditLog> editHistory;

}
