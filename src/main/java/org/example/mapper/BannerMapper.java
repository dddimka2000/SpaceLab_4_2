package org.example.mapper;

import org.example.dto.BannerDto;
import org.example.dto.BannerSlideDto;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BannerMapper {
    BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);

    void updateBannerFromDto(BannerDto bannerDto, @MappingTarget Banner banner);

    default String multipartFileToResultFilename(MultipartFile multipartFile) {
        if (multipartFile != null) {
            String uuidFile = UUID.randomUUID().toString();
            return uuidFile + "." + multipartFile.getOriginalFilename();
        }
        return null;
    }

    @Mapping(target = "slides", ignore = true)
    void updateDtoFromBanner(Banner banner, @MappingTarget BannerDto bannerDto);

    default void updateDtoFromBannerSlide(BannerSlide slide, @MappingTarget BannerSlideDto slideDto) {
        if (slide.getImgPath() != null) {
            slideDto.setOldImgPath(slide.getImgPath());
        }
    }

}
