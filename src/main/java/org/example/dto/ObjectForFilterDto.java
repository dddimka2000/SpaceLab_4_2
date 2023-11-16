package org.example.dto;

import lombok.Data;
import org.example.entity.BuilderObject;

import java.time.LocalDate;
import java.util.List;

@Data
public class ObjectForFilterDto {
    private Integer page;
    private List<String> district;
    private Integer numberRooms;
    private Integer minFloor;
    private Integer maxFloor;
    private Integer minPrice;
    private Integer maxPrice;
    private List<String> topozone;
    private List<Integer> builderObject;
    private Integer plotAreaMin;
    private Integer plotAreaMax;
    private Integer minArea;
    private Integer maxArea;
    private String street;
    private LocalDate lastContactDate;
}
