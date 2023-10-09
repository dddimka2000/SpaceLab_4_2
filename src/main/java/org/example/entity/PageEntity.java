package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "page", schema = "my_bd", catalog = "")
public class PageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
}
