package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OwnershipType {
    CONTRACT("Контракт"),
    OTHER("Особый вид");
    private final String status;
}
