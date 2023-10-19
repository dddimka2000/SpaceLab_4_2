package org.example.util.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PropertyBuildStatus {
    FINISHED("Закончена"),
    IN_PROGRESS("В процессе"),
    NOT_STARTED("Еще не начата");

    private final String status;
}