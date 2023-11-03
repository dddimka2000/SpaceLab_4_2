package org.example.repository;

import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//fixme @Repository is not needed
@Repository
public interface BannerSlideRepository extends JpaRepository<BannerSlide,Integer> {


    // fixme there is no class that extends BannerSlide, this is not needed
    @Override
    <S extends BannerSlide> S save(S entity);
    List<BannerSlide> findByBanner(Banner banner);

    // fixme this is not needed
    @Override
    void deleteById(Integer integer);

    void deleteAllByBannerId(Integer id);
}
