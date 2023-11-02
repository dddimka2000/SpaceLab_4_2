package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_reviews", schema = "my_bd", catalog = "")
@Data
public class UserReview {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name, phone;
    private String content;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @JsonBackReference
    private UserEntity user;
}
