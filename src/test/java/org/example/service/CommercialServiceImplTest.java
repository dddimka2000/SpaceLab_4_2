package org.example.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CommercialServiceImplTest {

    @Mock
    private PropertyCommercialObjectRepository propertyCommercialObjectRepository;

    @Mock
    private MinioService minioService;

    @Mock
    private RealtorServiceImpl realtorService;

    @Mock
    private CommercialInfoMapper commercialInfoMapper;

    @Mock
    private CommercialMaterialAndAreaMapper commercialMaterialAndAreaMapper;

    @Mock
    private CommercialAddressMapper commercialAddressMapper;

    @InjectMocks
    private CommercialServiceImpl commercialService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCount() {
        when(propertyCommercialObjectRepository.count()).thenReturn(5L);

        long result = commercialService.count();

        verify(propertyCommercialObjectRepository, times(1)).count();
        // Assert the result or any other expectations
    }

    @Test
    public void testAdd() throws Exception {
        CommercialInfoDto infoDto = new CommercialInfoDto();
        CommercialMaterialAndAreaDto materialAndAreaDto = new CommercialMaterialAndAreaDto();
        CommercialAddressDto addressDto = new CommercialAddressDto();

        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        commercialObject.setId(1);


        commercialService.add(infoDto, materialAndAreaDto, addressDto);

        verify(commercialInfoMapper, times(1)).updateEntityFromDto(eq(infoDto), any(), eq(minioService), eq(realtorService));
        verify(commercialMaterialAndAreaMapper, times(1)).updateEntityFromDto(eq(materialAndAreaDto), any());
        verify(commercialAddressMapper, times(1)).updateEntityFromDto(eq(addressDto), any());
        verify(propertyCommercialObjectRepository, times(1)).save(any());
    }
    @Test
    public void testAddWithId() throws Exception {
        CommercialInfoDto infoDto = new CommercialInfoDto();
        infoDto.setId(1);
        CommercialMaterialAndAreaDto materialAndAreaDto = new CommercialMaterialAndAreaDto();
        CommercialAddressDto addressDto = new CommercialAddressDto();

        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        commercialObject.setId(1);
        when(propertyCommercialObjectRepository.findById(1)).thenReturn(Optional.of(commercialObject));


        commercialService.add(infoDto, materialAndAreaDto, addressDto);

        verify(commercialInfoMapper, times(1)).updateEntityFromDto(eq(infoDto), any(), eq(minioService), eq(realtorService));
        verify(commercialMaterialAndAreaMapper, times(1)).updateEntityFromDto(eq(materialAndAreaDto), any());
        verify(commercialAddressMapper, times(1)).updateEntityFromDto(eq(addressDto), any());
        verify(propertyCommercialObjectRepository, times(1)).save(any());
    }

    @Test
    public void testGetById() {
        PropertyCommercialObject commercialObject = new PropertyCommercialObject();
        commercialObject.setId(1);

        when(propertyCommercialObjectRepository.findById(1)).thenReturn(Optional.of(commercialObject));

        PropertyCommercialObject result = commercialService.getById(1);

        verify(propertyCommercialObjectRepository, times(1)).findById(1);
        // Assert the result or any other expectations
    }

    @Test
    public void testGetAll() {
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
        propertyCommercialObject.setNumberOfElement(5);
        propertyCommercialObject.setPage(1);

        when(propertyCommercialObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());;

        Page<PropertyCommercialObject> result = commercialService.getAll(propertyCommercialObject);

        assertNotNull(result);

        // Verify that the repository method was called
        verify(propertyCommercialObjectRepository).findAll(any(Specification.class), any(Pageable.class));
        // Assert the result or any other expectations
    }

    @Test
    public void testDeleteById() {
        commercialService.deleteById(1);

        verify(propertyCommercialObjectRepository, times(1)).deleteById(1);
        // Assert any other expectations
    }
}
