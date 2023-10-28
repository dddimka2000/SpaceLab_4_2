package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KitchenStoveType {
    GAS("Газовая плита"),
    ELECTRIC("Електро плита");
    private final String status;
}
