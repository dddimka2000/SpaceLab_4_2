package org.example.repository;

import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LayoutRepository extends JpaRepository<Layout, Integer> {
    @Override
    Optional<Layout> findById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Override
    <S extends Layout> S save(S entity);

    List<Layout> findByBuilderObject(BuilderObject builderObject);

    void removeAllByBuilderObject(BuilderObject builderObject);
}
