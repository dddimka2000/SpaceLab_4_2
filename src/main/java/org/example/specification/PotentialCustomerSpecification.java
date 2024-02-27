package org.example.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.PotentialCustomer;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PotentialCustomerSpecification implements Specification<PotentialCustomer> {
    private String search;

    public PotentialCustomerSpecification(String search) {
        this.search = search;
    }

    @Override
    public Predicate toPredicate(Root<PotentialCustomer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(search != null){
            predicates.add(criteriaBuilder.like(root.get("fullName"), "%"+search+"%"));
            predicates.add(criteriaBuilder.like(root.get("phone"), "%"+search+"%"));
            predicates.add(criteriaBuilder.like(root.get("builderObject").get("name"), "%"+search+"%"));
            predicates.add(criteriaBuilder.like(root.get("viewDate"), "%"+search+"%"));
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.concat(
                            criteriaBuilder.concat(
                                    criteriaBuilder.lower(root.get("realtor").get("surname")),""
                            ),
                            criteriaBuilder.concat(
                                    " ",
                                    criteriaBuilder.lower(root.get("realtor").get("name"))
                            )
                    ),"%"+search+"%"));
            predicates.add(criteriaBuilder.like(root.get("registrationDate"), "%"+search+"%"));
            predicates.add(criteriaBuilder.like(root.get("statusPotentialCustomer"), "%"+search+"%"));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }
}
