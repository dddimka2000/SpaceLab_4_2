package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users", schema = "my_bd", catalog = "")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String email;
    private String password;
    @Transient private String confirmPassword;

    private UserRole role;

    private String name, surname, middleName;

    private String img;

    @OneToMany
    private List<Branch> branches;

    @OneToMany(mappedBy = "user")
    private List<UserReview> reviews;


}
