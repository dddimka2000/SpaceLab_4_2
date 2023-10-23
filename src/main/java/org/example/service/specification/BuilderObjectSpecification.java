package org.example.service.specification;

import jakarta.persistence.criteria.*;
import lombok.Data;
import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class BuilderObjectSpecification implements Specification<BuilderObject> {
    private String name;
    private String district;
    private String street;
    private String zone;
    private Integer minPrice;

    public BuilderObjectSpecification(String name, String district, String street, String zone, Integer minPrice) {
        this.name = name;
        this.district = district;
        this.street = street;
        this.zone = zone;
        this.minPrice = minPrice;
    }

    @Override
    public Predicate toPredicate(Root<BuilderObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("layouts").get("name"), "%" + name + "%"));
        }
        if (district != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("district"), "%" +district+ "%"));
        }
        if (street != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("street"), "%" +street+ "%"));
        }
        if (zone != null) {
            predicates.add(criteriaBuilder.like(root.get("address").get("zone"), "%" +zone+ "%"));
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
}
