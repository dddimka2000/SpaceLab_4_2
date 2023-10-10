package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "branch", schema = "my_bd", catalog = "")
@Data
public class Branch {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer code;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String imgPath;

}
