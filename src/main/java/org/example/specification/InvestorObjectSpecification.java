package org.example.specification;

import jakarta.persistence.criteria.*;
import lombok.Data;
import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.example.entity.property.PropertyInvestorObject;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data

public class InvestorObjectSpecification implements Specification<PropertyInvestorObject> {
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
    LocalDate lastContactDate;

    public InvestorObjectSpecification(List<String> district, Integer numberRooms, Integer minFloor, Integer maxFloor, Integer minPrice, Integer maxPrice, List<String> topozone, List<Integer> residentialComplexId, Integer minNumberFloors, Integer maxNumberFloors, Integer minArea, Integer maxArea, String street, LocalDate lastContactDate) {
        this.district = district;
        this.numberRooms = numberRooms;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.topozone = topozone;
        this.residentialComplexId = residentialComplexId;
        this.minNumberFloors = minNumberFloors;
        this.maxNumberFloors = maxNumberFloors;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.street = street;
        this.lastContactDate = lastContactDate;
    }

    @Override
    public Predicate toPredicate(Root<PropertyInvestorObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (district != null && district.size() > 0) {
            List<Predicate> districtPredicates = district.stream().map(d -> criteriaBuilder.like(root.get("address").get("district"), "%" + d + "%"))
                    .collect(Collectors.toList());
            Predicate orPredicate = criteriaBuilder.or(districtPredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);

        }
        if (numberRooms != null) {
            if(numberRooms == 4)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("roomQuantity"), numberRooms));
            else
                predicates.add(criteriaBuilder.equal(root.get("roomQuantity"), numberRooms));        }
        if (minFloor != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floor"), minFloor));
        }
        if (maxFloor != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("floor"), maxFloor));
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

        if (minNumberFloors != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floorQuantity"), minNumberFloors));
        }
        if (maxNumberFloors != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("floorQuantity"), maxNumberFloors));
        }
        if (minArea != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("areaTotal"), minArea));
        }
        if (maxArea != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("areaTotal"), maxArea));
        }
        if (street != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("street"), "%" + street + "%"));
        }
        if (residentialComplexId != null && residentialComplexId.size() > 0) {
            List<Predicate> residentialComplexIdPredicates = residentialComplexId.stream().map(d -> criteriaBuilder.equal(root.get("residentialComplexId"),d))
                    .collect(Collectors.toList());
            Predicate orPredicate = criteriaBuilder.or(residentialComplexIdPredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);
        }
        if (lastContactDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lastContactDate"), lastContactDate));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    public static Specification<PropertyInvestorObject> nameContains(String name) {
        if (name.isBlank() || name.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }


}
