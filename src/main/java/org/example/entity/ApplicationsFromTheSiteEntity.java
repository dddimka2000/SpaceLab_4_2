package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "applications_from_the_site", schema = "my_bd", catalog = "")
@Data
public class ApplicationsFromTheSiteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private String text;
    @Column(name = "contacts")
    private String contacts;
}
