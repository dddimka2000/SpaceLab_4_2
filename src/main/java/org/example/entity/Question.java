package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

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
