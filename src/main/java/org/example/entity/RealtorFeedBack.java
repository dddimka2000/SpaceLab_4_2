package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RealtorFeedBack {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String phone;
    private String description;
    @ManyToOne
    private Realtor realtor;
}
