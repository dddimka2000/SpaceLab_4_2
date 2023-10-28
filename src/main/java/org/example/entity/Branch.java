package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Entity
@Table(name = "branch")
@Accessors(chain = true)
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
