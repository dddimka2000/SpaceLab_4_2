package org.example.service;

import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BannerSlideDto;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.example.mapper.BannerSlideMapper;
import org.example.repository.BannerSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class BannerSlideService {
    final
    BannerSlideRepository bannerSlideRepository;

    public BannerSlideService(BannerSlideRepository bannerSlideRepository, MinioService minioService) {
        this.bannerSlideRepository = bannerSlideRepository;
        this.minioService = minioService;
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
    private String imagesBucketName = "images";


    final
    MinioService minioService;
    public void deleteAllByBannerId(Integer id) {
        bannerSlideRepository.deleteAllByBannerId(id);;
    }
    public List<BannerSlideDto> findAllByBannerIdDTO( List<BannerSlide> list) {
        List<BannerSlideDto> listDto = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        list.stream().forEach(s -> {
            BannerSlideDto bannerDto1 = new BannerSlideDto();
            BannerSlideMapper.INSTANCE.updateDtoFromEntity(s, bannerDto1);
            String path = null;
            try {
                path = minioService.getFileInString(s.getImgPath(), imagesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException
                     | NoSuchAlgorithmException | ServerException | XmlParserException | IOException e) {
                throw new RuntimeException(e);
            }
            bannerDto1.setOldImgPath(path);
            bannerDto1.setOldImgName(s.getImgPath());

            listDto.add(bannerDto1);
            atomicInteger.incrementAndGet();
        });
        return listDto;
    }
    public List<BannerSlide> findAllByBanner(Banner banner) {
        List<BannerSlide> list=bannerSlideRepository.findByBanner(banner);
        return list;
    }
}
