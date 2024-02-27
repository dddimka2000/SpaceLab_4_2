package org.example.repository;

import org.example.entity.PotentialCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PotentialCustomerRepository extends JpaRepository<PotentialCustomer, Integer>, JpaSpecificationExecutor {
}