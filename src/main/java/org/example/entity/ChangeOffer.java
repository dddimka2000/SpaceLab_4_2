package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.property._PropertyObject;

@Entity
@Table(name = "change_offers", schema = "my_bd", catalog = "")
@Data
public class ChangeOffer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String text;

    private String sender;

    @ManyToOne
    @JoinColumn(name = "id_object", referencedColumnName = "id")
    private _PropertyObject propertyObject;

}
