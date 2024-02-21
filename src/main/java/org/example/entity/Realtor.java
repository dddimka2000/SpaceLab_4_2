package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @Column(unique = true)
    private Integer code;

    private String name;
    private String surname;
    private String middleName;
    private String nameUK;
    private String surnameUK;
    private String middleNameUK;
    private String nameEN;
    private String surnameEN;
    private String middleNameEN;
    private String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;
    private List<String> files;
    private String img;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Branch branch;

    @OneToMany(mappedBy = "realtor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RealtorContact> contacts;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST} )
    private List<RealtorFeedBack> realtorFeedBacks;
    @OneToMany(mappedBy = "realtor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PotentialCustomer> potentialCustomers;
}
