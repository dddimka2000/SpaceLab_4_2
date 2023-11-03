package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.property.type.ContactType;

/*

fixme

would be great to set nullable/length on most fields

@Column (nullable = false, length = ...)
private String/Integer/...

 */

@Data
@Entity
public class RealtorContact {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String phone;

    // fixme might want to set @Enumerated(value = EnumType.STRING)
    // otherwise it gets saved in DB as an ordinal number - 0,1,2,3...

    private ContactType type;
    @ManyToOne
    @JoinColumn(name = "realtor_id")
    @JsonBackReference
    private Realtor realtor;
}
