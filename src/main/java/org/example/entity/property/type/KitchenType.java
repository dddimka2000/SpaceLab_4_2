package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KitchenType {
    STANDARD("Стандарт"),
    OTHER("Другое");
    private final String status;
}
