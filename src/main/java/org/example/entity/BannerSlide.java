package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banner_slides", schema = "my_bd", catalog = "")
@Data
public class BannerSlide {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;
    private Integer queue;
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private Banner banner;


}
