package org.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.property.type.InformationSource;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "buyers", catalog = "")
@Data
public class Buyer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String name;
    private String surname;
    private String middleName;
    private String phone;
    private String email;
    private String passport;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;

    private InformationSource informationSource;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate lastContactDate;

    private String comment;

    @ElementCollection
    private List<String> files;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Realtor realtor;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private BuyerApplication application;

    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Branch branch;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<BuyerNote> notes;

    @OneToMany(mappedBy = "buyer",cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Question> questions;
}
