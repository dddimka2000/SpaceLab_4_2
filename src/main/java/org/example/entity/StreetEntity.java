package org.example.entity;

import lombok.Data;
import jakarta.persistence.*;

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