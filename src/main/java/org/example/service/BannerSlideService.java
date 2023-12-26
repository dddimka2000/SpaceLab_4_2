package org.example.service;

import io.minio.errors.*;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BannerDto;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class BannerSlideService {
    final
    BannerSlideRepository bannerSlideRepository;
    final
    BannerService bannerService;


    public BannerSlideService(BannerSlideRepository bannerSlideRepository, MinioService minioService, BannerService bannerService) {
        this.bannerSlideRepository = bannerSlideRepository;
        this.minioService = minioService;
        this.bannerService = bannerService;
    }

    public void saveEdit(Optional<Banner> banner, BannerDto bannerDto) {
        List<BannerSlide> slideList = new ArrayList<>();
        AtomicInteger num_1 = new AtomicInteger(0);
        banner.get().getSlides().stream().forEach(s -> {
            if (s.getImgPath() == null) {
                s.setImgPath(bannerDto.getSlides().get(num_1.get()).getOldImgPath());
            }
            num_1.incrementAndGet();
        });

        //add new slides
        slideList.addAll(banner.get().getSlides());
        bannerService.save(banner.get());
        slideList.stream().forEach(s -> s.setBanner(banner.get()));
        slideList.stream().forEach(s -> save(s));
        AtomicInteger num = new AtomicInteger(0);

        //add new pict and delete old in MinIo
        bannerDto.getSlides().stream().forEach(s -> {
            if (s.getImgPath() != null) {
                try {
                    minioService.putMultipartFile(s.getImgPath(), imagesBucketName, slideList.get(num.get()).getImgPath());
                    if (s.getOldImgPath() != null)
                    {
                        minioService.deleteImg(s.getOldImgName(), imagesBucketName);
                    }
                }
                catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                         InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                         IOException e) {
                    throw new RuntimeException(e);
                }
            }
            num.incrementAndGet();
        });
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
        List<BannerSlide> list = bannerSlideRepository.findAll();
        log.info("BannerSlideService-findAll successfully");
        return list;
    }

    private String imagesBucketName = "images";


    final
    MinioService minioService;

    public void deleteAllByBannerId(Integer id) {
        bannerSlideRepository.deleteAllByBannerId(id);
        ;
    }

    public List<BannerSlideDto> findAllByBannerIdDTO(List<BannerSlide> list) {
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
        List<BannerSlide> list = bannerSlideRepository.findByBanner(banner);
        return list;
    }
}
