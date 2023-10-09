package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "offer_to_change", schema = "my_bd", catalog = "")
public class OfferToChangeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "text")
    private String text;
    @Column(name = "sender")
    private String sender;
    @ManyToOne
    @JoinColumn(name = "id_object", referencedColumnName = "id")
    private ObjectEntity objectByIdObject;
}
