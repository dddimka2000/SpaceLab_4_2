package org.example.repository;

import org.example.entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetEntityRepository extends JpaRepository<StreetEntity, Integer> {
}
