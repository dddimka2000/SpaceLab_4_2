package org.example.service;

import com.github.javafaker.Faker;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseForFilterDto;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    private static LocalDate generateRandomDate() {
        Faker faker = new Faker();
        LocalDate startDate = LocalDate.of(2023, 10, 10);
        LocalDate endDate = LocalDate.of(2023, 11, 13);
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = startEpochDay + faker.number().numberBetween(0, endEpochDay - startEpochDay + 1);

        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public Page<PropertyHouseObject> getAll(HouseForFilterDto houseForFilterDto) {
        Specification<PropertyHouseObject> specification = new HouseObjectSpecification(houseForFilterDto);
        Pageable pageable = PageRequest.of(houseForFilterDto.getPage(), 10, Sort.by(Sort.Order.desc("id")));
        return propertyHouseObjectRepository.findAll(specification, pageable);
    }
}
