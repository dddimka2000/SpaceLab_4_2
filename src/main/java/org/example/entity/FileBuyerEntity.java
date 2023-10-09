package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file_buyer", schema = "my_bd", catalog = "")
public class FileBuyerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "path_file")
    private String pathFile;
    @ManyToOne
    @JoinColumn(name = "id_buyer", referencedColumnName = "id")
    private BuyerEntity buyerByIdBuyer;
}
