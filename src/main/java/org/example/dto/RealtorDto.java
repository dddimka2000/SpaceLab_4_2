package org.example.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.Branch;
import org.example.entity.RealtorContact;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class RealtorDto {
    private Integer id;
    private String password;
    private Integer code;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private LocalDate birthdate;
    private MultipartFile img;
    private Branch branch;
    private List<RealtorContact> contacts;
}
