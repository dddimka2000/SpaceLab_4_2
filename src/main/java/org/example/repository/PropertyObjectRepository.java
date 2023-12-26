package org.example.repository;

import org.example.entity.property._PropertyObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyObjectRepository extends JpaRepository<_PropertyObject, Integer> {
    Boolean existsByObjectCode(String code);
    boolean existsByOwnerPhone(String phone);
}
