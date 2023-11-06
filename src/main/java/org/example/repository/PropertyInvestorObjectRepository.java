package org.example.repository;

import org.example.entity.property.PropertyInvestorObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyInvestorObjectRepository extends JpaRepository<PropertyInvestorObject, Integer> {

}
