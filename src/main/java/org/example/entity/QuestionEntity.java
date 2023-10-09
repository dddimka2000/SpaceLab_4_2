package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "question", schema = "my_bd", catalog = "")
@Data
public class QuestionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
}
