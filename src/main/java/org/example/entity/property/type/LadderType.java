package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LadderType {
    CONCRETE("Конкретная"), BEST_LADDER("Лучшая");
    private final String status;
}
