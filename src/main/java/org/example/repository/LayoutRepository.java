package org.example.repository;

import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//fixme @Repository is not needed
@Repository
public interface LayoutRepository extends JpaRepository<Layout, Integer> {


    //fixme this is not needed
    @Override
    Optional<Layout> findById(Integer integer);


    //fixme this is not needed
    @Override
    void deleteById(Integer integer);


    //fixme this is not needed
    @Override
    <S extends Layout> S save(S entity);

    List<Layout> findByBuilderObject(BuilderObject builderObject);


    void deleteAllByBuilderObject(BuilderObject builderObject);
}
