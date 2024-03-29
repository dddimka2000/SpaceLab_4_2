package org.example.specification;

import jakarta.persistence.criteria.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.example.entity.Branch;
import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@Log4j2
public class BuilderObjectSpecification implements Specification<BuilderObject> {
    private String name;
    private String district;
    private String street;
    private String zone;
    private Integer minPrice;
    private Integer floorQuantity;


    public BuilderObjectSpecification(String name, String district,String zone, String street,  Integer floorQuantity,Integer minPrice ) {
        this.name = name;
        this.district = district;
        this.zone = zone;
        this.street = street;
        this.minPrice = minPrice;
        this.floorQuantity = floorQuantity;
    }

    @Override
    public Predicate toPredicate(Root<BuilderObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (district != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("district"), "%" +district+ "%"));
        }
        log.info(zone);
        if (zone != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("zone"), "%" +zone+ "%"));
        }
        if (street != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("street"), "%"+street+"%"));
        }
        if (floorQuantity != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("floorQuantity"), floorQuantity));
        }

        if (minPrice != null) {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Layout> layoutRoot = subquery.from(Layout.class);
            subquery.select(criteriaBuilder.min(layoutRoot.get("price")));
            subquery.where(criteriaBuilder.equal(layoutRoot.get("builderObject"), root));

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(subquery, minPrice));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static Specification<BuilderObject> nameContains(String name) {
        if(name.isBlank() || name.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
