package org.example.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", schema = "my_bd", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "pass")
    private String pass;
    @Column(name = "role")
    private String role;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "code")
    private Integer code;
    @Column(name = "img_path")
    private String imgPath;
    @OneToMany(mappedBy = "userByIdRealtorUser")
    private List<BuyerEntity> buyersById;
    @OneToMany(mappedBy = "userByIdUser")
    private List<ObjectEntity> objectsById;
    @OneToMany(mappedBy = "userByIdUser")
    private List<ReviewUserEntity> reviewUsersById;
    @OneToMany(mappedBy = "userByUserId")
    private List<ReviewUserFileEntity> reviewUserFilesById;
    @OneToMany(mappedBy = "userByIdUser")
    private List<TableUsersBranchesEntity> tableUsersBranchesById;
    @OneToMany(mappedBy = "userByIdUser")
    private List<TelephoneUserEntity> telephoneUsersById;

}
