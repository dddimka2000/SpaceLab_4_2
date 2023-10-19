package org.example.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class StreetEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private DistrictEntity district;

}