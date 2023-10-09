package org.example.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "notes_buyers", schema = "my_bd", catalog = "")
public class NotesBuyersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "id_buyer", referencedColumnName = "id")
    private BuyerEntity buyerByIdBuyer;

}
