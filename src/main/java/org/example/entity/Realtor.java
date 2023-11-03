package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class Realtor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    // fixme if realtor cabinet is a separate app - these fields are unnecessary
    // i don't remember why i put them here
    private String password;
    @Transient private String confirmPassword;

    private Integer code;

    private String name;
    private String surname;
    private String middleName;
    private String email;

    // fixme pattern yyyy-MM-dd also works if you want to change it
    // don't remember why i set yyyy/MM/dd

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthdate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    // fixme @ElementCollection ?
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
