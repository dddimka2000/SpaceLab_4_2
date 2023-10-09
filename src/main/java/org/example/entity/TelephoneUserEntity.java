package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "telephone_user", schema = "my_bd", catalog = "")
@Data
public class TelephoneUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "social_network")
    private String socialNetwork;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity userByIdUser;
}
