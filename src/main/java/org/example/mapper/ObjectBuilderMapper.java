package org.example.mapper;

import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.entity.BuilderObject;
import org.example.entity.BuilderObjectPromotion;
import org.example.entity.property.type.PropertyBuildStatus;
import org.example.entity.property.type.PropertyObjectAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ObjectBuilderMapper {
    org.example.mapper.ObjectBuilderMapper INSTANCE = Mappers.getMapper(org.example.mapper.ObjectBuilderMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "nameObject"),
            @Mapping(target = "nameEnglish", source = "nameObjectEng"),
            @Mapping(target = "nameUkraine", source = "nameObjectUkr"),
            @Mapping(target = "floorQuantity", source = "floorQuantity"),
            @Mapping(target = "phone", source = "telephone"),
            @Mapping(target = "description_builder", source = "description"),
            @Mapping(target = "description_builderEng", source = "descriptionEng"),
            @Mapping(target = "description_builderUkr", source = "descriptionUkr"),
            @Mapping(target = "buildStatus", source = "buildStatus"),
            @Mapping(target = "nameCompany", source = "nameCompany"),
            @Mapping(target = "filePrices", source = "prices", qualifiedByName = "map"),
            @Mapping(target = "fileInstallmentTerms", source = "installmentTerms", qualifiedByName = "map"),
            @Mapping(target = "fileCheckerboard", source = "chessboardFile", qualifiedByName = "map"),
            @Mapping(target = "layouts", source = "layoutDTOList"),
            @Mapping(target = "buyerApplications", ignore = true),
            @Mapping(target = "description", source = "descriptionPromotion"),
            @Mapping(target = "descriptionEng", source = "descriptionPromotionEng"),
            @Mapping(target = "descriptionUkr", source = "descriptionPromotionUkr")
    })
    default BuilderObject toEntity(ObjectBuilderDto objectBuilderDto) {
        BuilderObject builderObject=new BuilderObject();
        PropertyObjectAddress propertyObjectAddress = new PropertyObjectAddress();
        propertyObjectAddress.setCity(objectBuilderDto.getCity());
        propertyObjectAddress.setDistrict(objectBuilderDto.getDistrict());
        propertyObjectAddress.setHouseNumber(objectBuilderDto.getHouseNumber());
        propertyObjectAddress.setRegion(objectBuilderDto.getRegion());
        propertyObjectAddress.setSection(objectBuilderDto.getSection());
        propertyObjectAddress.setStreet(objectBuilderDto.getStreet());
        propertyObjectAddress.setStreetUkr(objectBuilderDto.getStreetUkr());
        propertyObjectAddress.setStreetEng(objectBuilderDto.getStreetEng());
        propertyObjectAddress.setZone(objectBuilderDto.getTopozone());
        builderObject.setAddress(propertyObjectAddress);
        builderObject.setName(objectBuilderDto.getNameObject());
        builderObject.setNameEnglish(objectBuilderDto.getNameObjectEng());
        builderObject.setNameUkraine(objectBuilderDto.getNameObjectUkr());
        builderObject.setFloorQuantity(objectBuilderDto.getFloorQuantity());
        builderObject.setPhone(objectBuilderDto.getTelephone());
        builderObject.setDescription_builder(objectBuilderDto.getDescription());
        builderObject.setDescription_builderEng(objectBuilderDto.getDescriptionEng());
        builderObject.setDescription_builderUkr(objectBuilderDto.getDescriptionUkr());
        builderObject.setNameCompany(objectBuilderDto.getNameCompany());
        builderObject.setBuildStatus(PropertyBuildStatus.valueOf(objectBuilderDto.getBuildStatus()));
        BuilderObjectPromotion builderObjectPromotion = new BuilderObjectPromotion();
        builderObjectPromotion.setName(objectBuilderDto.getPromotionName());
        builderObjectPromotion.setNameEng(objectBuilderDto.getPromotionNameEng());
        builderObjectPromotion.setNameUkr(objectBuilderDto.getPromotionNameUkr());
        builderObjectPromotion.setDescription(objectBuilderDto.getDescriptionPromotion());
        builderObjectPromotion.setDescriptionEng(objectBuilderDto.getDescriptionPromotionEng());
        builderObjectPromotion.setDescriptionUkr(objectBuilderDto.getDescriptionPromotionUkr());
        Boolean statusPromotion = Boolean.parseBoolean(objectBuilderDto.getStatusPromotion());
        builderObjectPromotion.setActive(statusPromotion);
        builderObject.setPromotion(builderObjectPromotion);
        return builderObject;
    }

    @Named("map")
    default String map(MultipartFile source) {
        String result = null;
        if (source != null && !source.isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            result = uuidFile + "." + source.getOriginalFilename();

        }
        return result;
    }
    default void toEntity(BuilderObject builderObject, ObjectBuilderDtoEdit objectBuilderDto) {
        PropertyObjectAddress propertyObjectAddress = new PropertyObjectAddress();
        propertyObjectAddress.setCity(objectBuilderDto.getCity());
        propertyObjectAddress.setDistrict(objectBuilderDto.getDistrict());
        propertyObjectAddress.setHouseNumber(objectBuilderDto.getHouseNumber());
        propertyObjectAddress.setRegion(objectBuilderDto.getRegion());
        propertyObjectAddress.setSection(objectBuilderDto.getSection());
        propertyObjectAddress.setStreet(objectBuilderDto.getStreet());
        propertyObjectAddress.setStreetUkr(objectBuilderDto.getStreetUkr());
        propertyObjectAddress.setStreetEng(objectBuilderDto.getStreetEng());
        propertyObjectAddress.setZone(objectBuilderDto.getTopozone());
        builderObject.setAddress(propertyObjectAddress);
        builderObject.setName(objectBuilderDto.getNameObject());
        builderObject.setNameEnglish(objectBuilderDto.getNameObjectEng());
        builderObject.setNameUkraine(objectBuilderDto.getNameObjectUkr());
        builderObject.setFloorQuantity(objectBuilderDto.getFloorQuantity());
        builderObject.setPhone(objectBuilderDto.getTelephone());
        builderObject.setDescription_builder(objectBuilderDto.getDescription());
        builderObject.setDescription_builderEng(objectBuilderDto.getDescriptionEng());
        builderObject.setDescription_builderUkr(objectBuilderDto.getDescriptionUkr());
        builderObject.setNameCompany(objectBuilderDto.getNameCompany());
        builderObject.setBuildStatus(PropertyBuildStatus.valueOf(objectBuilderDto.getBuildStatus()));
        BuilderObjectPromotion builderObjectPromotion = new BuilderObjectPromotion();
        builderObjectPromotion.setName(objectBuilderDto.getPromotionName());
        builderObjectPromotion.setNameEng(objectBuilderDto.getPromotionNameEng());
        builderObjectPromotion.setNameUkr(objectBuilderDto.getPromotionNameUkr());
        builderObjectPromotion.setDescription(objectBuilderDto.getDescriptionPromotion());
        builderObjectPromotion.setDescriptionEng(objectBuilderDto.getDescriptionPromotionEng());
        builderObjectPromotion.setDescriptionUkr(objectBuilderDto.getDescriptionPromotionUkr());
        Boolean statusPromotion = Boolean.parseBoolean(objectBuilderDto.getStatusPromotion());
        builderObjectPromotion.setActive(statusPromotion);
        builderObject.setPromotion(builderObjectPromotion);
    }
}
