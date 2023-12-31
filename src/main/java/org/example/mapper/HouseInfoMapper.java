package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseInfoDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.type.PropertyOrigin;
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
        propertyHouseObject.setPropertyOrigin(PropertyOrigin.HOUSE);
        if(propertyHouseObject.getFiles() == null) propertyHouseObject.setFiles(new ArrayList<>());
        if(houseInfoDto.getFiles() != null)
        for (MultipartFile file : houseInfoDto.getFiles()) {
            propertyHouseObject.getFiles().add(minioService.putImage(file));
        }

        if(propertyHouseObject.getPictures() == null) propertyHouseObject.setPictures(new ArrayList<>());
        if(houseInfoDto.getPictures() != null)
        for (MultipartFile file : houseInfoDto.getPictures()) {
            propertyHouseObject.getPictures().add(minioService.putImage(file));
        }
        propertyHouseObject.setRealtor(realtorService.getByCode(houseInfoDto.getCodeStaff()));
    }
}
