package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "banner_img", schema = "my_bd", catalog = "")
@Data
public class BannerImgEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "queue")
    private Integer queue;
    @Column(name = "img_path")
    private String imgPath;
    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private BannerEntity bannerByBannerId;


}
