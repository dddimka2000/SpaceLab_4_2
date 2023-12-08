package org.example.mapper;

import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.entity.BuilderObject;
import org.example.entity.property.PropertyInvestorObject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Mapper(componentModel = "spring")
//public interface ObjectBuilderMapper {
//    org.example.mapper.ObjectBuilderMapper INSTANCE = Mappers.getMapper(org.example.mapper.ObjectBuilderMapper.class);
//
//    @Mappings({
//            @Mapping(target = "name", source = "nameObject"),
//            @Mapping(target = "nameEnglish", source = "nameObjectEng"),
//            @Mapping(target = "nameUkraine", source = "nameObjectUkr"),
//            @Mapping(target = "floorQuantity", source = "floorQuantity"),
//            @Mapping(target = "phone", source = "telephone"),
//            @Mapping(target = "description_builder", source = "description"),
//            @Mapping(target = "description_builderEng", source = "descriptionEng"),
//            @Mapping(target = "description_builderUkr", source = "descriptionUkr"),
//            @Mapping(target = "buildStatus", source = "buildStatus"),
//            @Mapping(target = "nameCompany", source = "nameCompany"),
//            @Mapping(target = "filePrices", source = "prices",qualifiedByName = "map"),
//            @Mapping(target = "fileInstallmentTerms", source = "installmentTerms",qualifiedByName = "map"),
//            @Mapping(target = "fileCheckerboard", source = "chessboardFile",qualifiedByName = "map"),
//            @Mapping(target = "promotion.name", source = "promotionName"),
//            @Mapping(target = "promotion.description", source = "descriptionPromotion"),
//            @Mapping(target = "layouts", source = "layoutDTOList"),
//            @Mapping(target = "buyerApplications", ignore = true),
//            @Mapping(target = "name", source = "promotionName"),
//            @Mapping(target = "nameEng", source = "promotionNameEng"),
//            @Mapping(target = "nameUkr", source = "promotionNameUkr"),
//            @Mapping(target = "description", source = "descriptionPromotion"),
//            @Mapping(target = "descriptionEng", source = "descriptionPromotionEng"),
//            @Mapping(target = "descriptionUkr", source = "descriptionPromotionUkr")
//    })
//    BuilderObject toEntity(ObjectBuilderDto entity);
//
//    @Named("map")
//    default String map(MultipartFile source) {
//        String result = null;
//        if (source != null && !source.isEmpty()) {
//            String uuidFile = UUID.randomUUID().toString();
//            result = uuidFile + "." + source.getOriginalFilename();
//
//        }
//        return result;
//    }
//
////    @Mappings({
////            @Mapping(target = "pictures", source = "pictures", ignore = true),
////            @Mapping(target = "realtor", source = "employeeCode", ignore = true),
////            @Mapping(target = "files", source = "files", qualifiedByName = "map"),
////            @Mapping(target = "nameObject", source = "name")
////    })
////    void toOldEntity(@MappingTarget ObjectBuilderDto propertyInvestorObject, BuilderObject entity);
//}
