package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entity.property.type.InformationSource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

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

    // fixme @Enumerated(value = EnumType.STRING) ?
    private InformationSource informationSource;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate lastContactDate;

    private String comment;

    @ElementCollection
    private List<String> files;

    @ManyToOne
    // fixme @JoinColumn ? it's present on one field , but not the other
    private Realtor realtor;

    @OneToOne
    private BuyerApplication application;

    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private Branch branch;

    @OneToMany(mappedBy = "buyer")
    private List<BuyerNote> notes;

}
