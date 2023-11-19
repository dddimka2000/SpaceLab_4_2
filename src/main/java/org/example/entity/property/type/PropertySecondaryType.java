package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PropertySecondaryType {
    PRIVATE("Приватная"),  // SEPARATE
    COMMUNAL("Комунальная");
    private final String status;
}
