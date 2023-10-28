package org.example.specification;

import org.example.entity.Branch;
import org.springframework.data.jpa.domain.Specification;

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
