package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.Buyer;
import org.example.service.MinioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface BuyerMapper {
    @Mapping(target = "files", ignore = true)
    Buyer toEntity(BuyerPersonalDataDto buyerPersonalDataDto);
    default Buyer toEntity(BuyerPersonalDataDto buyerPersonalDataDto, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Buyer buyer = toEntity(buyerPersonalDataDto);
        buyer.setFiles(new ArrayList<>());
        for (MultipartFile file : buyerPersonalDataDto.getFiles()) {
            buyer.getFiles().add(minioService.putImage(file));
        }
        return buyer;
    }

    @Mapping(target = "files", ignore = true)
    void updateEntityFromDto(BuyerPersonalDataDto buyerPersonalDataDto, @MappingTarget Buyer buyer);
    default void updateEntityFromDto(BuyerPersonalDataDto buyerPersonalDataDto, Buyer buyer, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(buyerPersonalDataDto, buyer);
        if(buyer.getFiles() == null) buyer.setFiles(new ArrayList<>());
        if(buyerPersonalDataDto.getFiles() != null)
        for (MultipartFile file : buyerPersonalDataDto.getFiles()) {
            buyer.getFiles().add(minioService.putImage(file));
        }
    }
}
