package org.example.service;

import com.github.javafaker.Faker;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.ObjectForFilterDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.mapper.HouseAddressMapper;
import org.example.mapper.HouseInfoMapper;
import org.example.mapper.HouseMaterialMapper;
import org.example.repository.PropertyHouseObjectRepository;
import org.example.service.specification.HouseObjectSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HousesServiceImpl {
    private final PropertyHouseObjectRepository propertyHouseObjectRepository;
    private final HouseMaterialMapper houseMaterialMapper;
    private final HouseAddressMapper houseAddressMapper;
    private final RealtorServiceImpl realtorService;
    private final HouseInfoMapper houseInfoMapper;
    private final MinioService minioService;

    public void add(HouseMaterialDto materialDTO, HouseInfoDto infoDTO, HouseAddressDto addressDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PropertyHouseObject house = new PropertyHouseObject();
        if(infoDTO.getId()!=null)house = getById(infoDTO.getId());
        PropertyObjectAddress address = new PropertyObjectAddress();
        houseAddressMapper.updateEntityFromDto(addressDTO, address);
        houseInfoMapper.updateEntityFromDto(infoDTO, house, minioService, realtorService);
        houseMaterialMapper.updateEntityFromDto(materialDTO, house);
        house.setAddress(address);
        save(house);
    }
    @Transactional
    public void save(PropertyHouseObject house) {
        propertyHouseObjectRepository.save(house);
    }

    public PropertyHouseObject getById(Integer id) {
        return propertyHouseObjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A house with an id = "+id +" was not found"));
    }

    public Page<PropertyHouseObject> getAll(ObjectForFilterDto objectForFilterDto) {
        Specification<PropertyHouseObject> specification = new HouseObjectSpecification(objectForFilterDto);
        Pageable pageable = PageRequest.of(objectForFilterDto.getPage(), 10, Sort.by(Sort.Order.desc("id")));
        return propertyHouseObjectRepository.findAll(specification, pageable);
    }

    public void deleteById(Integer id) {
        propertyHouseObjectRepository.deleteById(id);
    }
}
