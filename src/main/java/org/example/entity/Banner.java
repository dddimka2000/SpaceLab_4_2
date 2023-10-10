package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "banner", schema = "my_bd", catalog = "")
public class Banner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private String status;

    @OneToMany(mappedBy = "banner")
    private List<BannerSlide> slides;
}
