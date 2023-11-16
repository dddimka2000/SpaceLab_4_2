package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HouseProjectType {
    SPECIAL("Специальный"), BASIC("Обычный");
    private final String status;
}
