package org.example.specification;

import jakarta.persistence.criteria.*;
import org.example.entity.Realtor;
import org.example.entity.RealtorContact;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RealtorSpecification {

    public static Specification<Realtor> codeContains(String code) {
        if (code.isBlank() || code.isEmpty()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("code").as(String.class)), "%" + code.toLowerCase() + "%");
    }

    public static Specification<Realtor> fullNameContains(String fullName) {
        if (fullName.isBlank()) return (root, query, criteriaBuilder) -> null;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.concat(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(root.get("surname")),
                                                " "
                                        ),
                                        criteriaBuilder.lower(root.get("name"))
                                ),
                                criteriaBuilder.concat(
                                        " ",
                                        criteriaBuilder.lower(root.get("middleName"))
                                )
                        ),
                        "%" + fullName.toLowerCase() + "%"
                );
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


    public static Specification<Realtor> phoneContains(String phone) {
        if (phone.isBlank()) return (root, query, criteriaBuilder) -> null;

        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Realtor> subRoot = subquery.from(Realtor.class);
            Join<Realtor, RealtorContact> contactJoin = subRoot.joinList("contacts");

            subquery.select(subRoot.get("id")).where(criteriaBuilder.like(criteriaBuilder.lower(contactJoin.get("phone")), "%" + phone.toLowerCase() + "%"));

            return criteriaBuilder.in(root.get("id")).value(subquery);
        };
    }

    public static Specification<Realtor> branchContains(String branch) {
        if (branch.isBlank()) return (root, query, criteriaBuilder) -> null;

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("branch").get("id").as(String.class)),
                    "%" + branch.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Realtor> birthdateInRange(LocalDate birthdateMin, LocalDate birthdateMax) {
        return (root, query, criteriaBuilder) -> {
            if (birthdateMin == null && birthdateMax == null) {
                return null;
            }
            if (birthdateMin == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthdate"), birthdateMax);
            }
            if (birthdateMax == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthdate"), birthdateMin);
            }
            return criteriaBuilder.between(root.get("birthdate"), birthdateMin, birthdateMax);
        };
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
