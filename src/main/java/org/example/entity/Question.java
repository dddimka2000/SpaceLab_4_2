package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions", catalog = "")
@Data
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name, email, phone;

    private String question;
    private String answer;
    @ManyToOne
    @JoinColumn(name="id_buyer", referencedColumnName = "id")
    @JsonBackReference
    private Buyer buyer;
}
