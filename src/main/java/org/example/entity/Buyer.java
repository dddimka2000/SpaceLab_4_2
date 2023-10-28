package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entity.property.type.InformationSource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "buyers")
@Accessors(chain = true)
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
    private Realtor realtor;

    @OneToOne
    private BuyerApplication application;

    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    @OneToMany(mappedBy = "buyer")
    private List<BuyerNote> notes;

}
