package org.example.entity.property.type;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PropertyObjectAddress {
    private String region, city, district, zone, street;
    private Integer houseNumber;
    private Integer section;
}
