package org.example.service;

import com.github.javafaker.Faker;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class HousesServiceImpl {

    private final PropertyHouseObjectRepository propertyHouseObjectRepository;
    private final HouseMaterialMapper houseMaterialMapper;
    private final HouseAddressMapper houseAddressMapper;
    private final RealtorServiceImpl realtorService;
    private final HouseInfoMapper houseInfoMapper;
    private final MinioService minioService;

    public long count() {
        log.info("HousesServiceImpl-count start");
        long result = propertyHouseObjectRepository.count();
        log.info("HousesServiceImpl-count successfully");
        return result;
    }

    public void add(HouseMaterialDto materialDTO, HouseInfoDto infoDTO, HouseAddressDto addressDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("HousesServiceImpl-add start");

        PropertyHouseObject house = new PropertyHouseObject();
        if (infoDTO.getId() != null) {
            house = getById(infoDTO.getId());
        }

        PropertyObjectAddress address = new PropertyObjectAddress();
        houseAddressMapper.updateEntityFromDto(addressDTO, address);
        houseInfoMapper.updateEntityFromDto(infoDTO, house, minioService, realtorService);
        houseMaterialMapper.updateEntityFromDto(materialDTO, house);
        house.setAddress(address);

        save(house);

        log.info("HousesServiceImpl-add successfully");
    }

    @Transactional
    public void save(PropertyHouseObject house) {
        log.info("HousesServiceImpl-save start");
        propertyHouseObjectRepository.save(house);
        log.info("HousesServiceImpl-save successfully");
    }

    public PropertyHouseObject getById(Integer id) {
        log.info("HousesServiceImpl-getById start");
        PropertyHouseObject result = propertyHouseObjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("A house with an id = " + id + " was not found"));
        log.info("HousesServiceImpl-getById successfully");
        return result;
    }

    public Page<PropertyHouseObject> getAll(ObjectForFilterDto objectForFilterDto) {
        log.info("HousesServiceImpl-getAll start");

        Specification<PropertyHouseObject> specification = new HouseObjectSpecification(objectForFilterDto);
        Pageable pageable = PageRequest.of(objectForFilterDto.getPage(), 10, Sort.by(Sort.Order.desc("id")));
        Page<PropertyHouseObject> result = propertyHouseObjectRepository.findAll(specification, pageable);

        log.info("HousesServiceImpl-getAll successfully");
        return result;
    }

    public void deleteById(Integer id) {
        log.info("HousesServiceImpl-deleteById start");
        propertyHouseObjectRepository.deleteById(id);
        log.info("HousesServiceImpl-deleteById successfully");
    }
}
