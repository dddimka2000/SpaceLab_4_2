package org.example.entity;
import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
public class DistrictEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String nameDistrict;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private CityEntity city;

    @OneToMany(mappedBy = "district")
    private List<StreetEntity> streetEntityList;
}
