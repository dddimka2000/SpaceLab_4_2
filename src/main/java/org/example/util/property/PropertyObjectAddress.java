package org.example.util.property;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PropertyObjectAddress {

    private String region, city, district, zone, street;
    private Integer houseNumber;
    private String section;

}
