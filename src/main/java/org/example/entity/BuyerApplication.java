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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @ToString.Exclude
    @ElementCollection
    private List<String> districts;
    @ElementCollection
    private List<String> topzones;
    private String comment;
    @OneToOne
    @JsonBackReference
    @ToString.Exclude
    private Buyer buyer;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Realtor realtor;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BuilderObject builderObject;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<BuyerApplicationEditLog> editHistory;

}
