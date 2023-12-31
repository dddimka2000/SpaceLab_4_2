package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "layouts", catalog = "")
@Data
public class Layout {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name, nameEng, nameUkr;
    private Integer price;
    private Integer roomQuantity;
    private Integer areaLiving, areaTotal, areaKitchen;
    private Boolean active;

    private String description, descriptionEng, descriptionUkr;

    private String img1;
    private String img2;
    private String img3;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObject builderObject;

}
