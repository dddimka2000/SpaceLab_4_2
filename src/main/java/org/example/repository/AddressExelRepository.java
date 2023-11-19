package org.example.repository;

import org.example.entity.StreetExelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressExelRepository extends JpaRepository<StreetExelEntity, Integer> {
    @Override
    <S extends StreetExelEntity> S save(S entity);

    Boolean existsByStreetName(String name);
}
