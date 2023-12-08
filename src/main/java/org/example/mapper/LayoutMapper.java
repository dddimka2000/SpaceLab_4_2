package org.example.mapper;

import org.example.dto.LayoutDTO;
import org.example.dto.LayoutDTOEdit;
import org.example.dto.ObjectBuilderDto;
import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface LayoutMapper {
    org.example.mapper.LayoutMapper INSTANCE = Mappers.getMapper(org.example.mapper.LayoutMapper.class);
    default Layout toEntity(LayoutDTO dto) {
        Layout layout = new Layout();
        layout.setName(dto.getNameLayout());
        layout.setNameEng(dto.getNameLayoutEng());
        layout.setNameUkr(dto.getNameLayoutUkr());
        layout.setPrice(dto.getPriceLayout());
        layout.setRoomQuantity(dto.getRoomQuantityLayout());
        layout.setAreaKitchen(dto.getAreaKitchenLayout());
        layout.setAreaLiving(dto.getAreaLivingLayout());
        layout.setAreaTotal(dto.getAreaTotalLayout());
        layout.setActive(dto.getStatusLayout());
        layout.setDescription(dto.getDescriptionLayout());
        layout.setDescriptionEng(dto.getDescriptionLayoutEng());
        layout.setDescriptionUkr(dto.getDescriptionLayoutUkr());
        return layout;
    }
    default Layout toEntity(LayoutDTOEdit dto) {
        Layout layout = new Layout();
        layout.setName(dto.getNameLayout());
        layout.setNameEng(dto.getNameLayoutEng());
        layout.setNameUkr(dto.getNameLayoutUkr());
        layout.setPrice(dto.getPriceLayout());
        layout.setRoomQuantity(dto.getRoomQuantityLayout());
        layout.setAreaKitchen(dto.getAreaKitchenLayout());
        layout.setAreaLiving(dto.getAreaLivingLayout());
        layout.setAreaTotal(dto.getAreaTotalLayout());
        layout.setActive(dto.getStatusLayout());
        layout.setDescription(dto.getDescriptionLayout());
        layout.setDescriptionEng(dto.getDescriptionLayoutEng());
        layout.setDescriptionUkr(dto.getDescriptionLayoutUkr());
        return layout;
    }
}
