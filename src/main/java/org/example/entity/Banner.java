package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "banner", catalog = "")
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
