package org.example.entity;
import lombok.Data;
import jakarta.persistence.*;

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
