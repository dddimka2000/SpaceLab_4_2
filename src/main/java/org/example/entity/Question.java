package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions", schema = "my_bd", catalog = "")
@Data
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name, email, phone;

    private String question;
    private String answer;

}
