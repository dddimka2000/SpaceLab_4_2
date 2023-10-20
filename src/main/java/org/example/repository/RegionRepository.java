package org.example.repository;

import org.example.entity.RegionEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {
    @Override
    <S extends RegionEntity> List<S> findAll(Example<S> example);
}
