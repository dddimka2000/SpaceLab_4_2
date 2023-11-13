package org.example.repository;

import org.example.entity.property.PropertyHouseObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyHouseObjectRepository extends JpaRepository<PropertyHouseObject, Integer>, JpaSpecificationExecutor {
}
