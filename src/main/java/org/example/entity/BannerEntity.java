package org.example.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "banner", schema = "my_bd", catalog = "")
public class BannerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "bannerByBannerId")
    private List<BannerImgEntity> bannerImaginesById;
}
