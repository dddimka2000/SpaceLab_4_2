package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "layouts", schema = "my_bd", catalog = "")
public class Layout {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;
    private Integer price;
    private Integer roomQuantity;
    private Integer areaLiving, areaTotal, areaKitchen;
    private Boolean active;

    private String description;

    private String img1;
    private String img2;
    private String img3;

    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObject builderObject;

}
