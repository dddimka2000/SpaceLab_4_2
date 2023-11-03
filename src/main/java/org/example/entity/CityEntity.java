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

/*

fixme

why is this class needed

 */

@Data
@Entity
public class CityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String nameCity;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private RegionEntity region;

    @OneToMany(mappedBy = "city")
    private List<DistrictEntity> districtEntityList;

}