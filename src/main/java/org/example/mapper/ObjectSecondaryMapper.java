package org.example.mapper;

import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.property.PropertySecondaryObject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Mapper(componentModel = "spring")
public interface ObjectSecondaryMapper {
    org.example.mapper.ObjectSecondaryMapper INSTANCE = Mappers.getMapper(org.example.mapper.ObjectSecondaryMapper.class);

    @Mappings({
            @Mapping(target = "pictures", source = "pictures", ignore = true),
            @Mapping(target = "realtor", source = "employeeCode", ignore = true),
            @Mapping(target = "files", source = "files", qualifiedByName = "map")
    })
    PropertySecondaryObject toEntity(PropertySecondaryObjectDTO entity);

    @Named("map")
    default List<String> map(List<MultipartFile> source) {
        List<String> result = new ArrayList<>();
        if (source != null && !source.isEmpty()) {
            for (MultipartFile file : source) {
                String uuidFile = UUID.randomUUID().toString();
                result.add(uuidFile + "." + file.getOriginalFilename());
            }
        }
        return result;
    }

    @Mappings({
            @Mapping(target = "pictures", source = "pictures", ignore = true),
            @Mapping(target = "realtor", source = "employeeCode", ignore = true),
            @Mapping(target = "files", source = "files", qualifiedByName = "map")
    })
    void toOldEntity(@MappingTarget PropertySecondaryObject propertySecondaryObject, PropertySecondaryObjectDTO entity);


}
