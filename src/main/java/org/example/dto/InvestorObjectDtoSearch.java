package org.example.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvestorObjectDtoSearch {
    List<String> district;
    Integer numberRooms;
    Integer minFloor;
    Integer maxFloor;
    Integer minPrice;
    Integer maxPrice;
    List<String> topozone;
    List<Integer> residentialComplexId;
    Integer minNumberFloors;
    Integer maxNumberFloors;
    Integer minArea;
    Integer maxArea;
    String street;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    LocalDate lastContactDate;
}
