package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Entity
@Table(name = "banner_slides", catalog = "")
@Data
public class BannerSlide {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;
    private Integer queue;
    private String imgPath;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private Banner banner;


}
