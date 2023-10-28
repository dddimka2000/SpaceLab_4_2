package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pages", schema = "my_bd", catalog = "")
@Data
public class PageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

}
