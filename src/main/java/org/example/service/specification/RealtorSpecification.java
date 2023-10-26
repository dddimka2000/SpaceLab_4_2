package org.example.service.specification;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import org.example.entity.Branch;
import org.example.entity.Realtor;
import org.example.entity.RealtorContact;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RealtorSpecification {
    public static Specification<Realtor> branchIdContains(String branchId) {
        if (branchId.isBlank() || branchId.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("branch.id").as(String.class)), "%" + branchId.toLowerCase() + "%");
    }

    public static Specification<Realtor> codeContains(String code) {
        if (code.isBlank() || code.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("code").as(String.class)), "%" + code.toLowerCase() + "%");
    }

    public static Specification<Realtor> middlenameContains(String middlename) {
        if (middlename.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("middleName")),
                        "%" + middlename.toLowerCase() + "%"
                );
    }

    public static Specification<Realtor> surnameContains(String surname) {
        if (surname.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("surname")),
                        "%" + surname.toLowerCase() + "%"
                );
    }
    public static Specification<Realtor> nameContains(String name) {
        if (name.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }






    public static Specification<Realtor> emailContains(String email) {
        if (email.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"
                );
    }

}
