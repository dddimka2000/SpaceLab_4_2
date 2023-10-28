package org.example.entity.property.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WindowViewType {
    SEA("Море"),
    NO_VIEW("Нет вида");
    private final String status;
}
