package org.example.repository;

import org.example.entity.BuilderObject;
import org.example.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuilderObjectRepository extends JpaRepository<BuilderObject, Integer> {
    @Override
    <S extends BuilderObject> S save(S entity);

    @Override
    Optional<BuilderObject> findById(Integer integer);

    Page<BuilderObject> findAll(Pageable pageable);

    @Override
    void deleteById(Integer integer);
}
