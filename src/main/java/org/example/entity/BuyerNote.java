package org.example.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */


// fixme @Data ?
@Entity
@Table(name = "buyer_notes", schema = "my_bd", catalog = "")
public class BuyerNote {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    private String comment;

    @ManyToOne
    private Buyer buyer;

}
