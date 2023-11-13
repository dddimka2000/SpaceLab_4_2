package org.example.repository;

import org.example.entity.BuilderObject;
import org.example.entity.property.PropertyInvestorObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyInvestorObjectRepository extends JpaRepository<PropertyInvestorObject, Integer> {
    Page<PropertyInvestorObject> findAll(Specification<PropertyInvestorObject> spec, Pageable pageable);
    Optional<PropertyInvestorObject> findByObjectCode(String code);

}
