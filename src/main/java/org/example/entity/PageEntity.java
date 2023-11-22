package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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


    private String nameUkr;

    private String nameEng;
    @Column(columnDefinition = "TEXT")
    private String contentUkr;
    @Column(columnDefinition = "TEXT")
    private String contentEng;
}
