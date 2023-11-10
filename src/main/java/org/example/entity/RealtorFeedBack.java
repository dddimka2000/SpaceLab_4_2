package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealtorFeedBack {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String description;

    public RealtorFeedBack(Integer id, String name, String phone, String description) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
    }
}
