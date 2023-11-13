package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseInfoDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.service.MinioService;
import org.example.service.RealtorServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseInfoMapper {
    @Mappings({
            @Mapping(target = "files", ignore = true),
            @Mapping(target = "pictures", ignore = true),
    })
    void updateEntityFromDto(HouseInfoDto houseInfoDto, @MappingTarget PropertyHouseObject propertyHouseObject);
    default void updateEntityFromDto(HouseInfoDto houseInfoDto, PropertyHouseObject propertyHouseObject, MinioService minioService, RealtorServiceImpl realtorService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(houseInfoDto, propertyHouseObject);
        List<String> files = new ArrayList<>();
        for (MultipartFile file : houseInfoDto.getFiles()) {
            files.add(minioService.putImage(file));
        }
        List<String> pictures = new ArrayList<>();
        for (MultipartFile file : houseInfoDto.getPictures()) {
            pictures.add(minioService.putImage(file));
        }
        propertyHouseObject.setRealtor(realtorService.getById(houseInfoDto.getCodeStaff()));
        propertyHouseObject.setFiles(files);
        propertyHouseObject.setPictures(pictures);
    }
}
