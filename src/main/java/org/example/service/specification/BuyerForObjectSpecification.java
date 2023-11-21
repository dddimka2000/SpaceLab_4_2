package org.example.service.specification;

import jakarta.persistence.criteria.*;
import lombok.extern.log4j.Log4j2;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.BuyerApplication;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property._PropertyObject;
import org.example.entity.property.type.PropertyOrigin;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Log4j2
public class BuyerForObjectSpecification  implements Specification<BuyerApplication> {
    String district;
    Integer numberRooms;
    Integer floor;
    Integer price;
    String topozone;
    Integer residentialComplexId;
    Integer realtorId;
    Integer area;
    PropertyOrigin propertyOrigin;

    public BuyerForObjectSpecification(_PropertyObject object) {
        district = object.getAddress().getDistrict();
        numberRooms = object.getRoomQuantity();
        topozone = object.getAddress().getZone();
        floor = object.getFloor();
        price = object.getPrice();
        realtorId = object.getRealtor().getId();
        residentialComplexId = object.getBuilderObject() == null?null:object.getBuilderObject().getId();
        area = object.getAreaTotal();
        propertyOrigin = object.getPropertyOrigin();
    }

    @Override
    public Predicate toPredicate(Root<BuyerApplication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        log.info("search buyers ");
        if(propertyOrigin != null){
            predicates.add(criteriaBuilder.equal(root.get("origin"), propertyOrigin));
            log.info(propertyOrigin);
        }
        if(district != null){
            Join<BuyerApplication, String> districts = root.join("districts");
            predicates.add(criteriaBuilder.equal(districts, district));
            log.info("district " + district);

        }
        if(topozone != null){
            Join<BuyerApplication, String> topzones = root.join("topzones");
            predicates.add(criteriaBuilder.equal(topzones, topozone));
            log.info("topozone " + topozone);

        }
        if (residentialComplexId != null) {
            predicates.add(criteriaBuilder.equal(root.get("builderObject").get("id"), residentialComplexId));
            log.info("residentialComplexId" + residentialComplexId);
        }
        if (numberRooms != 0) {
            if(numberRooms >= 4)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("roomQuantity"), 4));
            else
                predicates.add(criteriaBuilder.equal(root.get("roomQuantity"), numberRooms));
            log.info("numberRooms "+ numberRooms);
        }
        if (price != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("priceMax"), price));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("priceMin"), price));
            log.info("price " + price);

        }
        if (realtorId != null) {
            predicates.add(criteriaBuilder.equal(root.get("realtor").get("id"), realtorId));
            log.info("realtorId " + realtorId);
        }
        if (floor != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floorMax"), floor));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("floorMin"), floor));
            log.info("floor " + floor);
        }
        if (area != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("houseAreaMax"), area));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("houseAreaMin"), area));
            log.info("area " + area);

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
