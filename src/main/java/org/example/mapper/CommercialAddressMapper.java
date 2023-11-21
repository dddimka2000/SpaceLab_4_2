package org.example.mapper;

import org.example.dto.CommercialAddressDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommercialAddressMapper {
    void updateEntityFromDto(CommercialAddressDto commercialAddressDto, @MappingTarget PropertyObjectAddress propertyObjectAddress);
}
