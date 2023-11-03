package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.entity.Banner;
import org.example.entity.ExchangeRates;
import org.example.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

fixme

divide services by interfaces and implementations

do not return Optional<...> in services, throw not found exception if the value is not present
if you return Optional from services, Controller layer has to check if the value is present or not

 */

@Service
@Log4j2
public class BannerService {
    private final
    BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }
    public Optional<Banner> findByName(String name) {
        log.info("BannerService-findByName start");
        Optional<Banner> exchange_rates=bannerRepository.findByNameBanners(name);
        log.info("BannerService-findByName successfully");
        return exchange_rates;
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
