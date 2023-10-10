package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "website_applications", schema = "my_bd", catalog = "")
public class WebsiteApplication {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String text;
    private String contacts;
}
