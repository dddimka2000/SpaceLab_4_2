package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class CityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String nameCity;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @JsonManagedReference
    private RegionEntity region;

    @OneToMany(mappedBy = "city")
    @JsonManagedReference
    private List<DistrictEntity> districtEntityList;

}