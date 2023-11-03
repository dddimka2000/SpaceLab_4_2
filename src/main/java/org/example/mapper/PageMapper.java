package org.example.mapper;

import org.example.dto.PageEntityDto;
import org.example.entity.PageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// fixme you do not need to get INSTANCE of mapper when working with spring
// use @Autowired or something like that

@Mapper(componentModel = "spring")
public interface PageMapper {
    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    PageEntityDto toDto(PageEntity page);

    PageEntity toEntity(PageEntityDto page);

}

