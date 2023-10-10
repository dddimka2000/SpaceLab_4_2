package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pages", schema = "my_bd", catalog = "")
public class Page {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

}
