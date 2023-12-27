package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import io.minio.errors.*;
import org.example.dto.HouseAddressDto;
import org.example.dto.HouseInfoDto;
import org.example.dto.HouseMaterialDto;
import org.example.dto.ObjectForFilterDto;
import org.example.entity.Realtor;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.mapper.HouseAddressMapper;
import org.example.mapper.HouseInfoMapper;
import org.example.mapper.HouseMaterialMapper;
import org.example.repository.PropertyHouseObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HousesServiceImplTest {

    @Mock
    private PropertyHouseObjectRepository propertyHouseObjectRepository;

    @Mock
    private HouseMaterialMapper houseMaterialMapper;

    @Mock
    private HouseAddressMapper houseAddressMapper;

    @Mock
    private RealtorServiceImpl realtorService;

    @Mock
    private HouseInfoMapper houseInfoMapper;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private HousesServiceImpl housesService;

    @Test
    void count() {
        // Mock the behavior of the repository
        long countMock=5;
        when(propertyHouseObjectRepository.count()).thenReturn(countMock);

        // Call the service method
        long count = housesService.count();

        // Verify that the method behaves as expected
        assertEquals(countMock, count);

        // Verify that the repository method was called
        verify(propertyHouseObjectRepository).count();
    }

    @Test
    void add() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Arrange
        HouseMaterialDto materialDTO = new HouseMaterialDto();
        HouseInfoDto infoDTO = new HouseInfoDto();
        infoDTO.setId(1);
        HouseAddressDto addressDTO = new HouseAddressDto();

        // Mock the behavior of your collaborators
        PropertyHouseObject existingHouse = new PropertyHouseObject();
        when(propertyHouseObjectRepository.findById(1)).thenReturn(java.util.Optional.of(existingHouse));
        when(propertyHouseObjectRepository.save(any(PropertyHouseObject.class))).thenReturn(any());

        // Act
        assertDoesNotThrow(() -> housesService.add(materialDTO, infoDTO, addressDTO));

        // Assert/Verify
        verify(houseMaterialMapper).updateEntityFromDto(eq(materialDTO), any());
        verify(houseAddressMapper).updateEntityFromDto(eq(addressDTO), any());
        verify(houseInfoMapper).updateEntityFromDto(eq(infoDTO), eq(existingHouse), any(MinioService.class), any(RealtorServiceImpl.class));
        verify(propertyHouseObjectRepository).save(any());
    }
    @Test
    void save() {
        // Mock the behavior of the repository
        // ...

        // Call the service method
        PropertyHouseObject house = new PropertyHouseObject();
        assertDoesNotThrow(() -> housesService.save(house));

        // Verify that the repository method was called
        verify(propertyHouseObjectRepository).save(house);
    }

    @Test
    void getById() {
        // Mock the behavior of the repository
        when(propertyHouseObjectRepository.findById(1)).thenReturn(java.util.Optional.of(new PropertyHouseObject()));

        // Call the service method
        PropertyHouseObject house = housesService.getById(1);

        // Verify that the method behaves as expected
        assertNotNull(house);

        // Verify that the repository method was called
        verify(propertyHouseObjectRepository).findById(1);
    }

    @Test
    void getAll() {
        ObjectForFilterDto propertyCommercialObject = new ObjectForFilterDto();
        PropertyObjectAddress propertyObjectAddress=new PropertyObjectAddress();
        propertyObjectAddress.setStreet("Название улицы");
        propertyObjectAddress.setCity("Город");
        propertyObjectAddress.setDistrict("Район");
        propertyObjectAddress.setZone("Зона");
        propertyCommercialObject.setDistrict(new ArrayList<>());
        propertyCommercialObject.setMinFloor(5);
        propertyCommercialObject.setMaxFloor(5);
        propertyCommercialObject.setMaxPrice(5);
        propertyCommercialObject.setPage(1);
        when(propertyHouseObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());
        Page<PropertyHouseObject> page = housesService.getAll(propertyCommercialObject);

        // Verify that the method behaves as expected
        assertNotNull(page);

        // Verify that the repository method was called
        verify(propertyHouseObjectRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void deleteById() {
        // Mock the behavior of the repository
        doNothing().when(propertyHouseObjectRepository).deleteById(1);

        // Call the service method
        assertDoesNotThrow(() -> housesService.deleteById(1));

        // Verify that the repository method was called
        verify(propertyHouseObjectRepository).deleteById(1);
    }
}
