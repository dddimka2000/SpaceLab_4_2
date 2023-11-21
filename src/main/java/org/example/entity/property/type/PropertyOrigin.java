package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PropertyOrigin {
    BUILDER("От строителей"),
    INVESTOR("От инвесторов"),
    SECONDARY("Вторичная"),
    HOUSE("Дома и участки"),
    COMMERCIAL("Коммерческая");

    private final String status;
}
