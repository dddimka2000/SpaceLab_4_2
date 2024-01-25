package org.example.service;

import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dto.CommercialAddressDto;
import org.example.dto.CommercialInfoDto;
import org.example.dto.CommercialMaterialAndAreaDto;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.mapper.CommercialAddressMapper;
import org.example.mapper.CommercialInfoMapper;
import org.example.mapper.CommercialMaterialAndAreaMapper;
import org.example.repository.PropertyCommercialObjectRepository;
import org.example.specification.CommercialObjectSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommercialServiceImpl {

    private final PropertyCommercialObjectRepository propertyCommercialObjectRepository;
    private final MinioService minioService;
    private final RealtorServiceImpl realtorService;
    private final CommercialInfoMapper commercialInfoMapper;
    private final CommercialMaterialAndAreaMapper commercialMaterialAndAreaMapper;
    private final CommercialAddressMapper commercialAddressMapper;

    public long count() {
        log.info("CommercialServiceImpl-count start");
        long result = propertyCommercialObjectRepository.count();
        log.info("CommercialServiceImpl-count successfully");
        return result;
    }

    public void add(CommercialInfoDto commercialInfoDto,
                    CommercialMaterialAndAreaDto commercialMaterialAndAreaDto,
                    CommercialAddressDto commercialAddressDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("CommercialServiceImpl-add start");

        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        if (commercialInfoDto.getId() != null) {
            commercialObject = getById(commercialInfoDto.getId());
        }

        PropertyObjectAddress propertyObjectAddress = new PropertyObjectAddress();
        commercialInfoMapper.updateEntityFromDto(commercialInfoDto, commercialObject, minioService, realtorService);
        commercialMaterialAndAreaMapper.updateEntityFromDto(commercialMaterialAndAreaDto, commercialObject);
        commercialAddressMapper.updateEntityFromDto(commercialAddressDto, propertyObjectAddress);
        commercialObject.setAddress(propertyObjectAddress);

        save(commercialObject);

        log.info("CommercialServiceImpl-add successfully");
    }

    public void save(PropertyCommercialObject commercialObject) {
        log.info("CommercialServiceImpl-save start");
        propertyCommercialObjectRepository.save(commercialObject);
        log.info("CommercialServiceImpl-save successfully");
    }

    public PropertyCommercialObject getById(Integer id) {
        log.info("CommercialServiceImpl-getById start");
        PropertyCommercialObject result = propertyCommercialObjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A commercial object with an id = " + id + " was not found"));
        log.info("CommercialServiceImpl-getById successfully");
        return result;
    }

    public Page<PropertyCommercialObject> getAll(ObjectForFilterDto objectForFilterDto) {
        log.info("CommercialServiceImpl-getAll start");

        Specification<PropertyCommercialObject> specification = new CommercialObjectSpecification(objectForFilterDto);
        Pageable pageable = PageRequest.of(objectForFilterDto.getPage(), objectForFilterDto.getNumberOfElement(), Sort.by(Sort.Order.desc("id")));
        Page<PropertyCommercialObject> result = propertyCommercialObjectRepository.findAll(specification, pageable);

        log.info("CommercialServiceImpl-getAll successfully");
        return result;
    }

    public void deleteById(Integer id) {
        log.info("CommercialServiceImpl-deleteById start");
        propertyCommercialObjectRepository.deleteById(id);
        log.info("CommercialServiceImpl-deleteById successfully");
    }
}

