package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.property.type.ContactType;

@Data
@Entity
public class RealtorContact {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String phone;
    private ContactType type;
    @ManyToOne
    @JoinColumn(name = "realtor_id")
    @JsonBackReference
    @ToString.Exclude
    private Realtor realtor;
}
