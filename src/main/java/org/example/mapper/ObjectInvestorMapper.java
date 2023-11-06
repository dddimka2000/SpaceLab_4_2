package org.example.mapper;

import org.example.dto.PageEntityDto;
import org.example.dto.PropertyInvestorObjectDTO;
import org.example.entity.PageEntity;
import org.example.entity.property.PropertyInvestorObject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ObjectInvestorMapper {
    org.example.mapper.ObjectInvestorMapper INSTANCE = Mappers.getMapper(org.example.mapper.ObjectInvestorMapper.class);
    @Mappings({
            @Mapping(target = "pictures", source = "pictures", ignore = true),
            @Mapping(target = "realtor", source = "realtor", ignore = true),
            @Mapping(target = "files", source = "files", qualifiedByName = "map") // Используйте квалификатор "map"
    })
    PropertyInvestorObject toEntity(PropertyInvestorObjectDTO entity);
    @Named("map")
    default List<String> map(List<MultipartFile> source) {
        List<String> result = new ArrayList<>();
        for (MultipartFile file : source) {
            result.add(file.getOriginalFilename()); // Пример: извлекаем имена файлов
        }
        return result;
    }



}
