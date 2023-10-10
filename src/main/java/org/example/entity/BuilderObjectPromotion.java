package org.example.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BuilderObjectPromotion {

    private String name, description;
    private Boolean active;
}
