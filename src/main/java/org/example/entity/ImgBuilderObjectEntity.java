package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "img_builder_object", schema = "my_bd", catalog = "")
@Data
public class ImgBuilderObjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "path_img")
    private String pathImg;
    @ManyToOne
    @JoinColumn(name = "id_build_object", referencedColumnName = "id")
    private BuilderObjectEntity builderObjectByIdBuildObject;


}
