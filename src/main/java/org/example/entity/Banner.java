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

@Entity
@Table(name = "banner", schema = "my_bd", catalog = "")
@Data
public class Banner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private String nameBanners;
    private Boolean status;

    @OneToMany(mappedBy = "banner")
    private List<BannerSlide> slides;
}
