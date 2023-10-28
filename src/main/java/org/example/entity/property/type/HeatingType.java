package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HeatingType {
    AUTOMATIC("Автоматическое"),
    NO_HEATING("Без отопления");
    private final String status;
}
