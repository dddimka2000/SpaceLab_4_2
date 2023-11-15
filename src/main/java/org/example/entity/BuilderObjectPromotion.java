package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BuilderObjectPromotion {
    private String name, nameEng, nameUkr, description, descriptionEng, descriptionUkr;
    private Boolean active;
}
