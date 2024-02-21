package org.example.repository;

import org.example.entity.PotentialCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotentialCustomerRepository extends JpaRepository<PotentialCustomer, Integer> {
}