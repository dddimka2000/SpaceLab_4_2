package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StreetExelEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String region;
    private String area; // Адміністративний район(старий)
    private String newArea; // Адміністративний район(новий)
    private String oTGName; // Найменування ОТГ(довідково)
    private String locality; // Населений пункт
    private Integer zipCode; // Індекс НП
    private String streetName; // Назва вулиці
    @Column(name = "house_numbers", length = 10000)
    private List<String> houseNumbers; // № будинку
}