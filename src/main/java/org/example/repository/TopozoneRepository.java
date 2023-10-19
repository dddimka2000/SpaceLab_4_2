package org.example.repository;

import org.example.entity.TopozoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopozoneRepository extends JpaRepository<TopozoneEntity, Integer> {
    @Override
    List<TopozoneEntity> findAll();
}
