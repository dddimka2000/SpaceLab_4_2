package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "promotion", schema = "my_bd", catalog = "")
@Data
public class PromotionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_builder_object", referencedColumnName = "id")
    private BuilderObjectEntity builderObjectByIdBuilderObject;


}
