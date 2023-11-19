package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LayoutType {
    SEPARATE("Раздельная"),
    JOINT("Общая");
    private final String status;

}
