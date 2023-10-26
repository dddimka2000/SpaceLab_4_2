package org.example.repository;

import org.example.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
    Optional<Banner> findByNameBanners(String name);

    @Override
    <S extends Banner> S save(S entity);

    @Override
    Optional<Banner> findById(Integer integer);
}
