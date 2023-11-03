package org.example.repository;

import org.example.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//fixme @Repository is not needed
@Repository
public interface CityRepository extends JpaRepository<CityEntity,Integer> {
  List<CityEntity> findByRegionNameRegion(String name);
}
