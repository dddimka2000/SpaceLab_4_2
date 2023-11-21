package org.example.repository;

import org.example.entity.property.PropertyCommercialObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyCommercialObjectRepository extends JpaRepository<PropertyCommercialObject, Integer>, JpaSpecificationExecutor {
}
