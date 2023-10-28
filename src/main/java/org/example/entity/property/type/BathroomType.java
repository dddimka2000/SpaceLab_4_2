package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BathroomType {
    TWO("Раздельный санузел"),
    ONE("Все вместе");

    private final String status;
}
