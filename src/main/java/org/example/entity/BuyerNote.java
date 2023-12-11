package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "buyer_notes", schema = "my_bd", catalog = "")
@Data
public class BuyerNote {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    private String comment;

    @ManyToOne
    @JsonBackReference
    private Buyer buyer;
}
