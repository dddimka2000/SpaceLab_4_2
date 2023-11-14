package org.example.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.dto.HouseForFilterDto;
import org.example.entity.property.PropertyHouseObject;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HouseObjectSpecification implements Specification<PropertyHouseObject> {
    List<String> district;
    Integer numberRooms;
    Integer minFloor;
    Integer maxFloor;
    Integer minPrice;
    Integer maxPrice;
    List<String> topozone;
    Integer plotAreaMin;
    Integer plotAreaMax;
    Integer minArea;
    Integer maxArea;
    String street;
    LocalDate lastContactDate;

    public HouseObjectSpecification(HouseForFilterDto houseForFilterDto) {
        district = houseForFilterDto.getDistrict();
        numberRooms = houseForFilterDto.getNumberRooms();
        minFloor = houseForFilterDto.getMinFloor();
        maxFloor = houseForFilterDto.getMaxFloor();
        minPrice = houseForFilterDto.getMinPrice();
        maxPrice = houseForFilterDto.getMaxPrice();
        topozone = houseForFilterDto.getTopozone();
        plotAreaMin = houseForFilterDto.getPlotAreaMin();
        plotAreaMax = houseForFilterDto.getPlotAreaMax();
        minArea = houseForFilterDto.getMinArea();
        maxArea = houseForFilterDto.getMaxArea();
        street = houseForFilterDto.getStreet();
        lastContactDate = houseForFilterDto.getLastContactDate();
    }

    @Override
    public Predicate toPredicate(Root<PropertyHouseObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (district != null && district.size() > 0) {
            List<Predicate> districtPredicates = district.stream().map(d -> criteriaBuilder.like(root.get("address").get("district"), "%" + d + "%"))
                    .collect(Collectors.toList());
            Predicate orPredicate = criteriaBuilder.or(districtPredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);

        }
        if (numberRooms != 0) {
            if(numberRooms == 4)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("roomQuantity"), numberRooms));
            else
                predicates.add(criteriaBuilder.equal(root.get("roomQuantity"), numberRooms));
        }
        if (minPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (topozone != null && topozone.size() > 0) {
            List<Predicate> topozonePredicates  = topozone.stream().map(d -> criteriaBuilder.like(root.get("address").get("zone"), "%" + d + "%"))
                    .collect(Collectors.toList());
            Predicate orPredicate = criteriaBuilder.or(topozonePredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);
        }

        if (minFloor != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floorQuantity"), minFloor));
        }
        if (maxFloor != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("floorQuantity"), maxFloor));
        }
        if (minArea != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("areaTotal"), minArea));
        }
        if (maxArea != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("areaTotal"), maxArea));
        }
        if (plotAreaMin != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("plotAreaTotal"), plotAreaMin));
        }
        if (plotAreaMax != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("plotAreaTotal"), plotAreaMax));
        }
        if (!street.isBlank()) {
            predicates.add(criteriaBuilder.like(root.get("address").get("street"), "%" + street + "%"));
        }
        if (lastContactDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastContactDate"), lastContactDate));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
