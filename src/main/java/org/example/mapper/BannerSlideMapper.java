package org.example.mapper;

import org.example.dto.BannerSlideDto;
import org.example.entity.BannerSlide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BannerSlideMapper {
    BannerSlideMapper INSTANCE = Mappers.getMapper(BannerSlideMapper.class);
    @Mapping(target = "imgPath", ignore = true)
    void updateDtoFromEntity(BannerSlide banner, @MappingTarget BannerSlideDto bannerDto);

}
