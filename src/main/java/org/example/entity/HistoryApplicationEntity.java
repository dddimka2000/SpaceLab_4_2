package org.example.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "history_application", schema = "my_bd", catalog = "")
public class HistoryApplicationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private ApplicationEntity applicationByApplicationId;
}
