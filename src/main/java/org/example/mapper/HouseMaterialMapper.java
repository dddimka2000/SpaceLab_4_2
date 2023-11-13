package org.example.mapper;

import org.example.dto.HouseAddressDto;
import org.example.dto.HouseMaterialDto;
import org.example.entity.property.PropertyHouseObject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HouseMaterialMapper {
    void updateEntityFromDto(HouseMaterialDto houseMaterialDto, @MappingTarget PropertyHouseObject propertyHouseObject);

}
