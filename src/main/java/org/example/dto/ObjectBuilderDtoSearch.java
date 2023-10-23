package org.example.dto;

import lombok.Data;

@Data
public class ObjectBuilderDtoSearch {
    String name;
    String district;
    String street;
    String topozone;
    Integer minPrice;
}
