package org.example.repository;

import org.example.entity.ImageEntity;
import org.example.util.TypeObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageForObjectsRepository extends JpaRepository<ImageEntity, Integer> {
    @Override
    <S extends ImageEntity> S save(S entity);

    List<ImageEntity> findByIdObjectAndTypeObject(Integer id, TypeObject typeObject);
}
