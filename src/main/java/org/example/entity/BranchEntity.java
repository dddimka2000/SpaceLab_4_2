package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "branch", schema = "my_bd", catalog = "")
@Data
public class BranchEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "e-mail")
    private String eMail;
    @Column(name = "code")
    private Integer code;
    @Column(name = "img_path")
    private String imgPath;
    @OneToMany(mappedBy = "branchByIdBranch")
    private List<BuyerEntity> buyersById;
    @OneToMany(mappedBy = "branchByIdBranch")
    private List<TableUsersBranchesEntity> tableUsersBranchesById;

}
