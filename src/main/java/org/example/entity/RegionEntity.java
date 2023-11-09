package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private List<CityEntity> cityEntityList;
}
