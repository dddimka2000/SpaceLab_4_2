package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
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

    @OneToMany(mappedBy = "realtor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RealtorContact> contacts;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST} )
    private List<RealtorFeedBack> realtorFeedBacks;
}
