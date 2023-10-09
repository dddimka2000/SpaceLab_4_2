package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "application")
@Data
public class ApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "kind")
    private String kind;
    @Column(name = "quantity_rooms")
    private Integer quantityRooms;
    @Column(name = "floor_min")
    private Integer floorMin;
    @Column(name = "floor_max")
    private Integer floorMax;
    @Column(name = "price_min")
    private Integer priceMin;
    @Column(name = "price_max")
    private Integer priceMax;
    @Column(name = "plot_area_min")
    private Integer plotAreaMin;
    @Column(name = "plot_area_max")
    private Integer plotAreaMax;
    @Column(name = "house_area_min")
    private Integer houseAreaMin;
    @Column(name = "house_area_max")
    private Integer houseAreaMax;
    @Column(name = "district")
    private String district;
    @Column(name = "topozone")
    private String topozone;
    @ManyToOne
    @JoinColumn(name = "id_buyer", referencedColumnName = "id")
    private BuyerEntity buyerByIdBuyer;
    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObjectEntity builderObjectByIdBuilderObject;
    @ManyToOne
    @JoinColumn(name = "id_object", referencedColumnName = "id")
    private ObjectEntity objectByIdObject;
    @OneToMany(mappedBy = "applicationByApplicationId")
    private List<HistoryApplicationEntity> historyApplicationsById;

}
