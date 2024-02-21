package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.example.entity.property.StatusPotentialCustomer;

import java.time.LocalDate;

@Data
@Entity
public class PotentialCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String phone;
    private LocalDate viewDate;
    private LocalDate registrationDate;
    private StatusPotentialCustomer statusPotentialCustomer;
    @ManyToOne
    @JsonBackReference
    private Realtor realtor;
    @ManyToOne
    private BuilderObject builderObject;
}
