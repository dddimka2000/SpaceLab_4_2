package org.example.mapper;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.service.MinioService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    @Mapping(target = "imgPath", ignore = true)
    Branch toEntity(BranchDto branchDto);

    default Branch toEntity(BranchDto branchDto, MinioService minioService) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Branch branch = toEntity(branchDto);
        branch.setImgPath(minioService.putImage(branchDto.getImgPath()));
        return branch;
    }
}