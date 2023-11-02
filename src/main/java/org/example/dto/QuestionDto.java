package org.example.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;

    private String name, email, phone;

    private String question;
    private String answer;
}
