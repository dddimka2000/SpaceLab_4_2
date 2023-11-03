package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.example.repository.BannerSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*

fixme

divide services by interfaces and implementations

 */

@Service
@Log4j2
public class BannerSlideService {

    //fixme private ? and fix formatting
    final
    BannerSlideRepository bannerSlideRepository;

    public BannerSlideService(BannerSlideRepository bannerSlideRepository) {
        this.bannerSlideRepository = bannerSlideRepository;
    }

    public void save(BannerSlide entity) {
        bannerSlideRepository.save(entity);
    }
    public void deleteById(Integer id) {
        log.info("BannerSlideService-deleteById start");
        bannerSlideRepository.deleteById(id);
        log.info("BannerSlideService-deleteById successfully");
    }
    public List<BannerSlide> findAll() {
        log.info("BannerSlideService-findAll start");
        List<BannerSlide> list=bannerSlideRepository.findAll();
        log.info("BannerSlideService-findAll successfully");
        return list;
    }
    public void deleteAllByBannerId(Integer id) {
        bannerSlideRepository.deleteAllByBannerId(id);;
    }
    public List<BannerSlide> findAllByBanner(Banner banner) {
        List<BannerSlide> list=bannerSlideRepository.findByBanner(banner);
        return list;
    }
}
