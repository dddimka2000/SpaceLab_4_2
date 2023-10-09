package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review_user", schema = "my_bd", catalog = "")
@Data
public class ReviewUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "name")
    private String name;
    @Column(name = "telephone")
    private String telephone;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity userByIdUser;


}
