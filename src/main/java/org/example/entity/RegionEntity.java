package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RegionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    String nameRegion;

    @OneToMany(mappedBy = "region")
    private List<CityEntity> cityEntityList;
}
