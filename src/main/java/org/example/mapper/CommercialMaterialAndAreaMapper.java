package org.example.mapper;

import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.entity.property.PropertyCommercialObject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface CommercialMaterialAndAreaMapper {
    void updateEntityFromDto(CommercialMaterialAndAreaDto commercialMaterialAndAreaDto, @MappingTarget PropertyCommercialObject propertyCommercialObject);
}
