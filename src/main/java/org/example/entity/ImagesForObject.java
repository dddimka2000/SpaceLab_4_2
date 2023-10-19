package org.example.entity;

import jakarta.persistence.*;
import org.example.util.TypeObject;

@Entity
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer idImage;
    @Enumerated(EnumType.STRING)
    TypeObject typeObject;
    String path;
    Integer idObject;
}
