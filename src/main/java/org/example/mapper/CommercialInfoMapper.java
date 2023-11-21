package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.CommercialInfoDto;
import org.example.entity.property.PropertyCommercialObject;
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

@Mapper(componentModel = "spring")
public interface CommercialInfoMapper {
    @Mappings({
            @Mapping(target = "files", ignore = true),
            @Mapping(target = "pictures", ignore = true),
    })
    void updateEntityFromDto(CommercialInfoDto commercialInfoDto, @MappingTarget PropertyCommercialObject commercialObject);
    default void updateEntityFromDto(CommercialInfoDto commercialInfoDto, PropertyCommercialObject commercialObject, MinioService minioService, RealtorServiceImpl realtorService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(commercialInfoDto, commercialObject);
        if(commercialObject.getFiles() == null)commercialObject.setFiles(new ArrayList<>());
        if(commercialInfoDto.getFiles() != null)
        for (MultipartFile file : commercialInfoDto.getFiles()) {
            commercialObject.getFiles().add(minioService.putImage(file));
        }

        if(commercialObject.getPictures() == null)commercialObject.setPictures(new ArrayList<>());
        if(commercialInfoDto.getPictures() != null)
        for (MultipartFile picture : commercialInfoDto.getPictures()) {
            commercialObject.getPictures().add(minioService.putImage(picture));
        }

        commercialObject.setRealtor(realtorService.getByCode(commercialInfoDto.getStaffCode()));
    }
}
