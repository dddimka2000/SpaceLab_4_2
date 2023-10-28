package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Realtor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String password;
    @Transient private String confirmPassword;

    private Integer code;

    private String name;
    private String surname;
    private String middleName;
    private String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    private List<String> files;
    private String img;

    @ManyToOne
    private Branch branch;

    @OneToMany(cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<RealtorContact> contacts;
    @OneToMany(mappedBy = "realtor", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<RealtorFeedBack> realtorFeedBacks;
}
