package org.example.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.example.entity.Branch;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<UserEntity> roleContains(String role) {
        if (role.isBlank() || role.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) -> {
            Join<UserEntity, String> roles = root.join("roles", JoinType.INNER);
            return criteriaBuilder.like(criteriaBuilder.lower(roles), "%" + role.toLowerCase() + "%");
        };
    }



    public static Specification<UserEntity> surnameContains(String surname) {
        if (surname.isBlank() || surname.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%");
    }
    public static Specification<UserEntity> nameContains(String name) {
        if(name.isBlank() || name.isEmpty())return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<UserEntity> middleNameContains(String middleName) {
        if (middleName.isBlank() || middleName.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), "%" + middleName.toLowerCase() + "%");
    }

    public static Specification<UserEntity> phoneContains(String phone) {
        if (phone.isBlank() || phone.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<UserEntity> emailContains(String email) {
        if (email.isBlank() || email.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static class BranchSpecification {
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
}
