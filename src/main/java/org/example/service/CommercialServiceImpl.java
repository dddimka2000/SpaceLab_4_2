package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.CommercialAddressDto;
import org.example.dto.CommercialInfoDto;
import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.mapper.CommercialAddressMapper;
import org.example.mapper.CommercialInfoMapper;
import org.example.mapper.CommercialMaterialAndAreaMapper;
import org.example.repository.PropertyCommercialObjectRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class CommercialServiceImpl {
    private final PropertyCommercialObjectRepository propertyCommercialObjectRepository;
    private final MinioService minioService;
    private final RealtorServiceImpl realtorService;
    private final CommercialInfoMapper commercialInfoMapper;
    private final CommercialMaterialAndAreaMapper commercialMaterialAndAreaMapper;
    private final CommercialAddressMapper commercialAddressMapper;
    public void add(CommercialInfoDto commercialInfoDto,
                    CommercialMaterialAndAreaDto commercialMaterialAndAreaDto,
                    CommercialAddressDto commercialAddressDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        if(commercialInfoDto.getId() != null)commercialObject = getById(commercialInfoDto.getId());
        commercialInfoMapper.updateEntityFromDto(commercialInfoDto, commercialObject, minioService, realtorService);
        commercialMaterialAndAreaMapper.updateEntityFromDto(commercialMaterialAndAreaDto, commercialObject);
        commercialAddressMapper.updateEntityFromDto(commercialAddressDto, commercialObject);
        save(commercialObject);
    }

    public void save(PropertyCommercialObject commercialObject) {
        propertyCommercialObjectRepository.save(commercialObject);
    }

    public PropertyCommercialObject getById(Integer id) {
        return propertyCommercialObjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A commercial object with an id = "+id +" was not found"));
    }
}
