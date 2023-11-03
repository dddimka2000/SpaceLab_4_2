package org.example.repository;

import org.example.entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//fixme @Repository is not needed
@Repository
public interface StreetRepository extends JpaRepository<StreetEntity, Integer> {
    List<StreetEntity> findByDistrictNameDistrict(String name);
}
