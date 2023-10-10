package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.util.ContactType;

@Data
@Entity
public class RealtorContact {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String phone;
    private ContactType type;

    @ManyToOne
    private Realtor realtor;
}
