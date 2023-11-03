package org.example.mapper;

import org.example.dto.BannerSlideDto;
import org.example.entity.BannerSlide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

// fixme you do not need to get INSTANCE of mapper when working with spring
// use @Autowired or something like that

// fixme component model = "spring"
@Mapper
public interface BannerSlideMapper {
    BannerSlideMapper INSTANCE = Mappers.getMapper(BannerSlideMapper.class);
    @Mapping(target = "imgPath", ignore = true)
    void updateDtoFromEntity(BannerSlide banner, @MappingTarget BannerSlideDto bannerDto);

}
