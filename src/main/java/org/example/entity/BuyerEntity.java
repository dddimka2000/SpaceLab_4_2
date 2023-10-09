package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "buyer", schema = "my_bd", catalog = "")
@Data
public class BuyerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "e-mail")
    private String eMail;
    @Column(name = "passport_series_number")
    private String passportSeriesNumber;
    @Column(name = "date_birth")
    private String dateBirth;
    @Column(name = "source_of_information")
    private String sourceOfInformation;
    @Column(name = "date_of_last_contact")
    private String dateOfLastContact;
    @Column(name = "comment")
    private String comment;
    @OneToMany(mappedBy = "buyerByIdBuyer")
    private List<ApplicationEntity> applicationsById;
    @ManyToOne
    @JoinColumn(name = "id_realtor_user", referencedColumnName = "id")
    private UserEntity userByIdRealtorUser;
    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id")
    private BranchEntity branchByIdBranch;
    @OneToMany(mappedBy = "buyerByIdBuyer")
    private List<FileBuyerEntity> fileBuyersById;
    @OneToMany(mappedBy = "buyerByIdBuyer")
    private Collection<NotesBuyersEntity> notesBuyersById;

}
