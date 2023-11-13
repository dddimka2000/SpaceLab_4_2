package org.example.mapper;

import org.example.dto.BuyerPersonalDataDto;
import org.example.dto.HouseAddressDto;
import org.example.entity.Buyer;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HouseAddressMapper {
    void updateEntityFromDto(HouseAddressDto houseAddressDto, @MappingTarget PropertyObjectAddress propertyObjectAddress);

}
