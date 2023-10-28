package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BalconyType {
    BALCONY("Балкон"),
    LODGE("Лоджия");
    private final String status;

}
