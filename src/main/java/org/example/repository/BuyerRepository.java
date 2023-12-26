package org.example.repository;

import org.example.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuyerRepository extends JpaRepository<Buyer, Integer>, JpaSpecificationExecutor{
    boolean existsByPhone(String phone);
}