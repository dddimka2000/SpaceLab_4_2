package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.example.service.MinioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface RealtorMapper {
    @Mappings({
            @Mapping(target = "img", ignore = true),
            @Mapping(target = "realtorFeedBacks", ignore = true),
            @Mapping(target = "files", ignore = true)
    })
    Realtor toEntity(RealtorDto realtorDto);
    default Realtor toEntity(RealtorDto realtorDto, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Realtor realtor = toEntity(realtorDto);
        realtor.setImg(minioService.putImage(realtorDto.getImg()));
        if(realtorDto.getFiles()!=null){
            realtor.setFiles(new ArrayList<>());
            for (MultipartFile file : realtorDto.getFiles()) {
                realtor.getFiles().add(minioService.putImage(file));
            }
        }
        return realtor;
    }
    @Mappings({
            @Mapping(target = "img", ignore = true),
            @Mapping(target = "realtorFeedBacks", ignore = true),
            @Mapping(target = "files", ignore = true)
    })
    void updateEntityFromDto(RealtorDto realtorDto, @MappingTarget Realtor realtor);
    default void updateEntityFromDto(RealtorDto realtorDto, Realtor realtor, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(realtorDto, realtor);
        if(!Objects.equals(realtorDto.getImg().getOriginalFilename(), "")) realtor.setImg(minioService.putImage(realtorDto.getImg()));
    }
}
