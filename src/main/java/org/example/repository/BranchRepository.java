package org.example.repository;

import org.example.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Page<Branch> findAllByNameContainingAndAddressContaining(String name, String address, Pageable pageable);
}