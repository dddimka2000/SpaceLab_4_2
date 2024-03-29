package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.RealtorDto;
import org.example.dto.UserDto;
import org.example.entity.Realtor;
import org.example.entity.UserEntity;
import org.example.service.MinioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "img", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    UserEntity toEntity(UserDto userDto);
    default UserEntity toEntity(UserDto userDto, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserEntity user = toEntity(userDto);
        user.setImg(minioService.putImage(userDto.getImg()));
        if(userDto.getPassword() != null)user.setPassword(user.getPassword());
        return user;
    }
    @Mappings({
            @Mapping(target = "img", ignore = true)
    })
    void updateEntityFromDto(UserDto userDto, @MappingTarget UserEntity user);
    default void updateEntityFromDto(UserDto userDto, UserEntity user, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(userDto, user);
        if(userDto.getImg()!=null)
            user.setImg(minioService.putImage(userDto.getImg()));
    }
}
