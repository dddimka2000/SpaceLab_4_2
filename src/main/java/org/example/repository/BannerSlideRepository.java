package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerSlideRepository extends JpaRepository<BannerSlide,Integer> {
    @Override
    <S extends BannerSlide> S save(S entity);
    List<BannerSlide> findByBanner(Banner banner);
    @Override
    void deleteById(Integer integer);

    void deleteAllByBannerId(Integer id);
}
