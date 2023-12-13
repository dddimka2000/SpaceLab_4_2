package org.example.specification;

import org.example.entity.Buyer;
import org.springframework.data.jpa.domain.Specification;

public class BuyerSpecification {
    public static Specification<Buyer> middleNameContains(String middleName) {
        if (middleName.isBlank() || middleName.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), "%" + middleName.toLowerCase() + "%");
    }
    public static Specification<Buyer> nameContains(String name) {
        if(name.isBlank() || name.isEmpty())return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<Buyer> surnameContains(String surname) {
        if (surname.isBlank() || surname.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%");
    }
    public static Specification<Buyer> branchContains(String branch) {
        if (branch.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("realtor").get("branch").get("id").as(String.class)),
                "%" + branch.toLowerCase() + "%"
        );
    }
    public static Specification<Buyer> realtorContains(String realtor) {
        if (realtor.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("realtor").get("id").as(String.class)),
                "%" + realtor.toLowerCase() + "%"
        );
    }
    public static Specification<Buyer> phoneContains(String phone) {
        if (phone.isBlank() || phone.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<Buyer> emailContains(String email) {
        if (email.isBlank() || email.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
    public static Specification<Buyer> priceGreaterThanOrEqual(String price) {
        if (price == null || price.isBlank()) return null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("application").get("priceMax"), Double.parseDouble(price));
    }
}
