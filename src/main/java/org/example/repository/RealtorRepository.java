package org.example.repository;

import org.example.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RealtorRepository extends JpaRepository<Realtor, Integer>, JpaSpecificationExecutor {
    Optional<Realtor> findFirstBy();
}
