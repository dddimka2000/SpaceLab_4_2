package org.example.service.specification;

import org.example.entity.Branch;
import org.springframework.data.jpa.domain.Specification;

/*

    fixme

    alternative approach
    public class BranchSpecification implements Specification<Branch> {

        ...

        @Override
        public Predicate toPredicate(Root... , Query..., CriteriaBuilder...) {

            and check all fields at the same time

        }

        sort order can also be added like so
        query.orderBy(criteriaBuilder.desc(root.get('...')))
    }

     */

    /*

    fixme

    why do you have specification here and specification in service package

     */


public class BranchSpecification {
    public static Specification<Branch> codeContains(String codeText) {
        if(codeText.isBlank() || codeText.isEmpty())return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("code").as(String.class)), "%" + codeText.toLowerCase() + "%");
    }
    public static Specification<Branch> nameContains(String name) {
        if(name.isBlank() || name.isEmpty())return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Branch> addressContains(String address) {
        if(address.isBlank() || address.isEmpty())return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }
}
