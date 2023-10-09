package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "table_users_branches", schema = "my_bd", catalog = "")
@Data
public class TableUsersBranchesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity userByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private BranchEntity branchByIdBranch;
}
