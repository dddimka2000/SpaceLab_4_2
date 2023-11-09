package org.example.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import jakarta.persistence.*;
import lombok.ToString;

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
    @JsonBackReference
    private CityEntity city;

    @OneToMany(mappedBy = "district")
    @JsonManagedReference
    private List<StreetEntity> streetEntityList;
}
