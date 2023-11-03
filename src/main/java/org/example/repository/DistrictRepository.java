package org.example.repository;

import org.example.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//fixme @Repository is not needed
@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity,Integer> {
    List<DistrictEntity> findByCityNameCity(String name);
}
