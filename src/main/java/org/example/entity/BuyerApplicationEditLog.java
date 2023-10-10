package org.example.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "history_application", schema = "my_bd", catalog = "")
public class BuyerApplicationEditLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String description;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private BuyerApplication application;
}
