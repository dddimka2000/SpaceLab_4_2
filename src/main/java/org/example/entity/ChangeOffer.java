package org.example.entity;

import jakarta.persistence.*;
import org.example.entity.property._PropertyObject;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */


// fixme @Data ?
@Entity
@Table(name = "change_offers", schema = "my_bd", catalog = "")
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
