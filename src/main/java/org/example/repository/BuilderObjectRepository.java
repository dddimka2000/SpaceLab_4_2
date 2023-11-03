package org.example.repository;

import org.example.entity.Branch;
import org.example.entity.BuilderObject;
import org.example.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//fixme @Repository is not needed
//fixme add specification executor , not method
@Repository
public interface BuilderObjectRepository extends JpaRepository<BuilderObject, Integer> {
    
    // fixme this is not needed
    @Override
    <S extends BuilderObject> S save(S entity);

    // fixme this is not needed
    @Override
    Optional<BuilderObject> findById(Integer integer);

    // fixme this is not needed
    Page<BuilderObject> findAll(Specification<BuilderObject> spec, Pageable pageable);
    
    
    Optional<BuilderObject> findByName(String name);


    // fixme this is not needed
    @Override
    void deleteById(Integer integer);


}
