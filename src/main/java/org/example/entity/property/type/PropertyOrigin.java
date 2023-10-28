package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PropertyOrigin {
    BUILDER("От строителей"),
    INVESTOR("От инвесторов");
    private final String status;
}
