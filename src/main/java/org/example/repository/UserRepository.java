package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// fixme add types to specification executor
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor {
    Optional<UserEntity> findByEmail(String email);
}
