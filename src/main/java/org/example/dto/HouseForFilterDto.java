package org.example.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HouseForFilterDto {
    private Integer page;
    private List<String> district;
    private Integer numberRooms;
    private Integer minFloor;
    private Integer maxFloor;
    private Integer minPrice;
    private Integer maxPrice;
    private List<String> topozone;
    private Integer plotAreaMin;
    private Integer plotAreaMax;
    private Integer minArea;
    private Integer maxArea;
    private String street;
    private LocalDate lastContactDate;
}
