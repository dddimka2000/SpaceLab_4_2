package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.Banner;
import org.example.entity.ExchangeRates;
import org.example.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BannerService {
    private final
    BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public Optional<Banner> findById(Integer id) {
        log.info("BannerService-findById start");
        Optional<Banner> banner=bannerRepository.findById(id);
        log.info("BannerService-findById successfully");
        return banner;
    }
    public void save(Banner entity) {
        bannerRepository.save(entity);
    }
    public void deleteById(Integer id) {
        log.info("BannerService-deleteById start");
        bannerRepository.deleteById(id);
        log.info("BannerService-deleteById successfully");
    }
    public List<Banner> findAll() {
        log.info("BannerService-findAll start");
        List<Banner> list=bannerRepository.findAll();
        log.info("BannerService-findAll successfully");
        return list;
    }
}
