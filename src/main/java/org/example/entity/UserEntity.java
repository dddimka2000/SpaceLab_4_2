package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", schema = "my_bd", catalog = "")
@Data
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String email;
    private String password;

    @Transient private String confirmPassword;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    private String name, surname, middleName;

    private String img;

    @OneToMany
    private List<Branch> branches;

    @OneToMany(mappedBy = "user")
    private List<UserReview> reviews;


}
