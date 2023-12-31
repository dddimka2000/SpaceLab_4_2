package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.property.type.TypeObject;

@Entity
@Data
public class ImagesForObject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer idImage;
    @Enumerated(EnumType.STRING)
    TypeObject typeObject;
    String path;
    Integer idObject;
}
