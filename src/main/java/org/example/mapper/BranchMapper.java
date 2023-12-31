package org.example.mapper;

import io.minio.errors.*;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.service.MinioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    @Mapping(target = "imgPath", ignore = true)
    Branch toEntity(BranchDto branchDto);
    @Mapping(target = "imgPath", ignore = true)
    void updateEntityFromDto(BranchDto branchDto, @MappingTarget Branch branch);
    default Branch toEntity(BranchDto branchDto, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Branch branch = toEntity(branchDto);
        branch.setImgPath(minioService.putImage(branchDto.getImgPath()));
        return branch;
    }
    default void updateEntityFromDto(BranchDto branchDto, Branch branch, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateEntityFromDto(branchDto, branch);
        if(!Objects.equals(branchDto.getImgPath().getOriginalFilename(), ""))branch.setImgPath(minioService.putImage(branchDto.getImgPath()));
    }
}