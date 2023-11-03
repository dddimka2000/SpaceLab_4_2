package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

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
