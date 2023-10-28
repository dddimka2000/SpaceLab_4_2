package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PublicationStatus {
    PUBLISHED("Опубликовано"),
    HIDDEN("В архиве"),
    IN_PROCESS("В процессе");
    private final String status;
}
