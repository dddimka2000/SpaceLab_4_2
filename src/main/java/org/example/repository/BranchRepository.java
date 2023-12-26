package org.example.repository;

import org.example.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer>, JpaSpecificationExecutor {
    int countByCode(int code);
    Optional<Branch> findByCode(Integer integer);
    boolean existsByTelephone(String phone);
}