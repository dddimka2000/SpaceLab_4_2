package org.example.repository;

import org.example.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RealtorRepository extends JpaRepository<Realtor, Integer>, JpaSpecificationExecutor {
}
