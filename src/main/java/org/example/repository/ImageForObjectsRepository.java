package org.example.repository;

import org.example.entity.ImagesForObject;
import org.example.entity.property.type.TypeObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//fixme @Repository is not needed
@Repository
public interface ImageForObjectsRepository extends JpaRepository<ImagesForObject, Integer> {


    //fixme this is not needed
    @Override
    <S extends ImagesForObject> S save(S entity);

    List<ImagesForObject> findByIdObjectAndTypeObject(Integer id, TypeObject typeObject);

    Optional<ImagesForObject> findFirstByIdObjectAndTypeObject(Integer id, TypeObject typeObject);

}
