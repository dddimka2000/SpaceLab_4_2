package org.example.repository;

import org.example.entity.BuyerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuyerApplicationRepository extends JpaRepository<BuyerApplication, Integer>, JpaSpecificationExecutor {
}
