package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Entity
@Table(name = "layouts", schema = "my_bd", catalog = "")
@Data
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

    // fixme why fetchType lazy
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObject builderObject;

}
