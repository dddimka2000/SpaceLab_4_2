package org.example.repository;

import org.example.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//fixme @Repository is not needed
@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
    Optional<Banner> findByNameBanners(String name);

    // fixme there is no class that extends Banner, this is not needed
    @Override
    <S extends Banner> S save(S entity);

    @Override
    Optional<Banner> findById(Integer integer);
}
