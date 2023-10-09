package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "layout", schema = "my_bd", catalog = "")
public class LayoutEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "quantity_rooms")
    private Integer quantityRooms;
    @Column(name = "living_area")
    private Integer livingArea;
    @Column(name = "total_area")
    private Integer totalArea;
    @Column(name = "kitchen_area")
    private Integer kitchenArea;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "img1")
    private String img1;
    @Column(name = "img2")
    private String img2;
    @Column(name = "img3")
    private String img3;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObjectEntity builderObjectByIdBuilderObject;

}
