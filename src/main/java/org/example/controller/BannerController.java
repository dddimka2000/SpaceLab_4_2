package org.example.controller;

import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.example.dto.BannerDto;
import org.example.dto.BannerSlideDto;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.example.mapper.BannerMapper;
import org.example.mapper.BannerSlideMapper;
import org.example.service.BannerService;
import org.example.service.BannerSlideService;
import org.example.service.MinioService;
import org.example.util.validator.BannerValidator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/banners")
@Log4j2
public class BannerController {
    String imagesBucketName = "images";

    private final
    BannerService bannerService;

    private final
    BannerSlideService bannerSlideService;
    private final
    MinioService minioService;
    private final
    BannerValidator bannerValidator;


    public BannerController(BannerService bannerService, BannerSlideService bannerSlideService, MinioService minioService, BannerValidator bannerValidator) {
        this.bannerService = bannerService;
        this.bannerSlideService = bannerSlideService;
        this.minioService = minioService;
        this.bannerValidator = bannerValidator;
    }

    @GetMapping
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("banners", bannerService.findAll());
        modelAndView.setViewName("banners/banners_table");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView editPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        BannerDto bannerDto = new BannerDto();
        Optional<Banner> byId = bannerService.findById(id);
        if(byId.isEmpty()){
            modelAndView.setViewName("error/404");
        }
        BannerMapper.INSTANCE.updateDtoFromBanner(byId.get(), bannerDto);
        modelAndView.addObject("banner", bannerDto);
        List<BannerSlide> list = bannerSlideService.findAllByBanner(byId.get());
        List<BannerSlideDto> listDto = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        list.stream().forEach(s -> {
            BannerSlideDto bannerDto1 = new BannerSlideDto();
            BannerSlideMapper.INSTANCE.updateDtoFromEntity(s, bannerDto1);
            String path = null;
            try {
                path = minioService.getFileInString(s.getImgPath(), imagesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException |InvalidKeyException | InvalidResponseException
                     | NoSuchAlgorithmException | ServerException | XmlParserException | IOException  e) {
                throw new RuntimeException(e);
            }
            bannerDto1.setOldImgPath(path);
                bannerDto1.setOldImgName(s.getImgPath());

            listDto.add(bannerDto1);
            atomicInteger.incrementAndGet();
        });

        modelAndView.addObject("bannerList", listDto);
        modelAndView.addObject("idModel", id);
        modelAndView.setViewName("banners/banners_edit");
        return modelAndView;
    }


    @PostMapping("/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity editPagePost(@PathVariable Integer id, @Valid @ModelAttribute BannerDto bannerDto, BindingResult bindingResult) {
        Optional<Banner> banner = bannerService.findById(id);
        bannerDto.getSlides().stream().forEach(s -> log.info("SLIDE DTO" + s));
        if (!bindingResult.hasErrors()) {
            bannerValidator.validate(bannerDto, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            log.error("error validation");
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        bannerSlideService.deleteAllByBannerId(id);
        BannerMapper.INSTANCE.updateBannerFromDto(bannerDto, banner.get());
        log.warn("banner " + banner.get());
        List<BannerSlide> slideList = new ArrayList<>();
        AtomicInteger num_1 = new AtomicInteger(0);
        banner.get().getSlides().stream().forEach(s -> {
            if (s.getImgPath() == null) {
                s.setImgPath(bannerDto.getSlides().get(num_1.get()).getOldImgPath());
            }
            num_1.incrementAndGet();
        });
        slideList.addAll(banner.get().getSlides());
        bannerService.save(banner.get());
        slideList.stream().forEach(s -> log.warn("slideList getImgPath " + s.getImgPath()));
        bannerDto.getSlides().stream().forEach(s -> log.warn("bannerDto getOldImgPath " + s.getOldImgPath()));
        bannerDto.getSlides().stream().forEach(s -> log.warn("bannerDto getImgPath " + s.getImgPath()));
        log.warn("banner " + banner.get());
        slideList.stream().forEach(s -> s.setBanner(banner.get()));
        slideList.stream().forEach(s -> bannerSlideService.save(s));
        AtomicInteger num = new AtomicInteger(0);
        bannerDto.getSlides().stream().forEach(s -> {
            if (s.getImgPath() != null) {
                try {
                    log.info(s.getImgPath().getOriginalFilename() + " minioSAVE");
                    log.info(slideList.get(num.get()).getImgPath() + "name minio");
                    minioService.putMultipartFile(s.getImgPath(), imagesBucketName, slideList.get(num.get()).getImgPath());
                    if (s.getOldImgPath() != null) {
                        log.info(s.getImgPath().getOriginalFilename() + " minioDelete");
                        minioService.deleteImg(s.getOldImgName(), imagesBucketName);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ServerException e) {
                    throw new RuntimeException(e);
                } catch (InsufficientDataException e) {
                    throw new RuntimeException(e);
                } catch (ErrorResponseException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (InvalidResponseException e) {
                    throw new RuntimeException(e);
                } catch (XmlParserException e) {
                    throw new RuntimeException(e);
                } catch (InternalException e) {
                    throw new RuntimeException(e);
                }
            }
            num.incrementAndGet();
        });
        return ResponseEntity.ok().body("ok");
    }

    @ModelAttribute
    public void activeMenuItem(Model model) {
        model.addAttribute("bannersActive", true);
    }
}
