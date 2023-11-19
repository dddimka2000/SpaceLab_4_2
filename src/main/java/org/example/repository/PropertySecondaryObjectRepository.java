package org.example.repository;

import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.PropertySecondaryObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertySecondaryObjectRepository extends JpaRepository<PropertySecondaryObject, Integer> {
    Optional<PropertySecondaryObject> findById(PropertySecondaryObject propertySecondaryObject);
    Optional<PropertySecondaryObject> findByObjectCode(String code);

    <T> Page<PropertySecondaryObject> findAll(Specification<T> where, Pageable pageable);
}
