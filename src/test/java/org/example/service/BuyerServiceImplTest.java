package org.example.service;

import org.example.dto.BuyerPersonalDataDto;
import org.example.entity.*;
import org.example.entity.property.PropertyCommercialObject;
import org.example.entity.property.PropertyHouseObject;
import org.example.entity.property.PropertyInvestorObject;
import org.example.entity.property.PropertySecondaryObject;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.mapper.BuyerMapper;
import org.example.repository.*;
import org.example.specification.BuyerForObjectSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceImplTest {

    @InjectMocks
    private BuyerServiceImpl buyerService;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private BuyerApplicationRepository buyerApplicationRepository;

    @Mock
    private BuyerNoteRepository buyerNoteRepository;

    @Mock
    private BuyerMapper buyerMapper;

    @Mock
    private MinioService minioService;

    @Mock
    private BuyerApplicationEditLogRepository buyerApplicationEditLogRepository;

    @Mock
    private CommercialServiceImpl commercialService;
    @Mock
    private PropertyCommercialObjectRepository commercialObjectRepository;

    @Mock
    private HousesServiceImpl housesService;

    @Mock
    private PropertyInvestorObjectService propertyInvestorObjectService;

    @Mock
    private PropertySecondaryObjectService propertySecondaryObjectService;

    @Test
    public void testAddPersonalData() throws Exception {
        // Arrange
        BuyerPersonalDataDto buyerDto = new BuyerPersonalDataDto();
        Buyer buyer = new Buyer();
        buyer.setId(1);
        Mockito.when(buyerMapper.toEntity(buyerDto, minioService)).thenReturn(buyer);
        Mockito.when(buyerRepository.save(buyer)).thenReturn(buyer);

        // Act
        buyerService.addPersonalData(buyerDto);
        // Assert
        Mockito.verify(buyerMapper, times(1)).toEntity(buyerDto, minioService);
        Mockito.verify(buyerRepository, times(1)).save(buyer);
    }
    @Test
    public void testAddPersonalDataWithoutId() throws Exception {
        // Arrange
        BuyerPersonalDataDto buyerDto = new BuyerPersonalDataDto();
        buyerDto.setId(1); // Assuming id is nullable
        Buyer buyer = new Buyer();
        buyer.setId(1); // Set a specific id value
        Mockito.when(buyerRepository.findById(1)).thenReturn(java.util.Optional.of(new Buyer()));
        doNothing().when(buyerMapper).updateEntityFromDto(any(), any(), any());
        when(buyerRepository.save(any(Buyer.class))).thenReturn(buyer);

        // Act
        buyerService.addPersonalData(buyerDto);

        // Assert
        verify(buyerMapper, times(1)).updateEntityFromDto(any(), any(), any());
        verify(buyerRepository, times(1)).save(any(Buyer.class));
    }
    @Test
    public void testGetById() {
        // Arrange
        int buyerId = 1;
        Mockito.when(buyerRepository.findById(buyerId)).thenReturn(java.util.Optional.of(new Buyer()));

        // Act
        Buyer result = buyerService.getById(buyerId);

        // Assert
        assertEquals(Buyer.class, result.getClass());
        Mockito.verify(buyerRepository, times(1)).findById(buyerId);
    }

    // Add tests for other methods in a similar manner

    @Test
    public void testDeleteNote() {
        // Arrange
        Integer noteId = 1;

        // Act
        buyerService.deleteNote(noteId);

        // Assert
        Mockito.verify(buyerNoteRepository, times(1)).deleteById(noteId);
    }

    @Test
    public void testGetAll() {
        // Arrange
        Integer page = 0;
        Integer numberOfElement = 1;
        String branch = "Branch";
        String realtor = "Realtor";
        String name = "Name";
        String phone = "Phone";
        String email = "Email";
        String price = "100000";

        // Act
        buyerService.getAll(page, numberOfElement, branch, realtor, name, phone, email, price);

        // Assert
        Mockito.verify(buyerRepository, times(1)).findAll(any(Specification.class),  any(Pageable.class));
    }
    @Test
    public void testAddApplication() {
        // Arrange
        BuyerApplication buyerApplication = new BuyerApplication();
        buyerApplication.setId(1);

        // Создаем реальный экземпляр Buyer, который не является макетом
        Buyer buyer = new Buyer();
        buyerApplication.setBuyer(buyer);

        // Act
        buyerService.addApplication(buyerApplication);

        // Assert
        verify(buyerApplicationRepository, times(1)).save(buyerApplication);
        verify(buyerRepository, times(1)).save(buyer);
        verify(buyerApplicationEditLogRepository, times(buyerApplication.getId() != null ? 1 : 0)).save(any(BuyerApplicationEditLog.class));
    }


    @Test
    public void testAddNote() {
        // Arrange
        BuyerNote buyerNote = new BuyerNote();

        // Act
        buyerService.addNote(buyerNote);

        // Assert
        verify(buyerNoteRepository, times(1)).save(buyerNote);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Integer buyerId = 1;

        // Act
        buyerService.deleteById(buyerId);

        // Assert
        verify(buyerRepository, times(1)).deleteById(buyerId);
    }

    @Test
    public void testFilterForObjectHOUSE() {
        // Arrange
        String type = "HOUSE";
        PropertyHouseObject propertyCommercialObject = new PropertyHouseObject();
        propertyCommercialObject.setId(1);
        PropertyObjectAddress propertyObjectAddress=new PropertyObjectAddress();
        propertyObjectAddress.setStreet("Название улицы");
        propertyObjectAddress.setCity("Город");
        propertyObjectAddress.setDistrict("Район");
        propertyObjectAddress.setZone("Зона");
        propertyCommercialObject.setAddress(propertyObjectAddress);
        propertyCommercialObject.setRoomQuantity(3);
        propertyCommercialObject.setFloor(5);
        propertyCommercialObject.setPrice(100000);  // Примерная цена
        propertyCommercialObject.setRealtor(new Realtor());
        propertyCommercialObject.setAreaTotal(150);  // Примерная площадь
        when(housesService.getById(anyInt())).thenReturn(propertyCommercialObject);
        // Use a real list instead of any(List.class)
        List<BuyerApplication> buyerApplications = new ArrayList<>();
        when(buyerApplicationRepository.findAll(any(BuyerForObjectSpecification.class))).thenReturn(buyerApplications);
        // Act
        List<Buyer> result = buyerService.filterForObject(anyInt(), type);
        // Assert
        verify(buyerApplicationRepository, times(1)).findAll(any(BuyerForObjectSpecification.class));
        // Add additional assertions based on your specific implementation and requirements
    }
    @Test
    public void testFilterForObjectINVESTOR() {
        // Arrange
        String type = "INVESTOR";
        PropertyInvestorObject propertyCommercialObject = new PropertyInvestorObject();
        propertyCommercialObject.setId(1);
        PropertyObjectAddress propertyObjectAddress=new PropertyObjectAddress();
        propertyObjectAddress.setStreet("Название улицы");
        propertyObjectAddress.setCity("Город");
        propertyObjectAddress.setDistrict("Район");
        propertyObjectAddress.setZone("Зона");
        propertyCommercialObject.setAddress(propertyObjectAddress);
        propertyCommercialObject.setRoomQuantity(3);
        propertyCommercialObject.setFloor(5);
        propertyCommercialObject.setPrice(100000);  // Примерная цена
        propertyCommercialObject.setRealtor(new Realtor());
        propertyCommercialObject.setAreaTotal(150);  // Примерная площадь
        when(propertyInvestorObjectService.findById(anyInt())).thenReturn(Optional.of(propertyCommercialObject));
        // Use a real list instead of any(List.class)
        List<BuyerApplication> buyerApplications = new ArrayList<>();
        when(buyerApplicationRepository.findAll(any(BuyerForObjectSpecification.class))).thenReturn(buyerApplications);
        // Act
        List<Buyer> result = buyerService.filterForObject(anyInt(), type);
        // Assert
        verify(buyerApplicationRepository, times(1)).findAll(any(BuyerForObjectSpecification.class));
        // Add additional assertions based on your specific implementation and requirements
    }

    @Test
    public void testFilterForObject() {
        // Arrange
        String type = "SECONDARY";
        PropertySecondaryObject propertyCommercialObject = new PropertySecondaryObject();
        propertyCommercialObject.setId(1);
        PropertyObjectAddress propertyObjectAddress=new PropertyObjectAddress();
        propertyObjectAddress.setStreet("Название улицы");
        propertyObjectAddress.setCity("Город");
        propertyObjectAddress.setDistrict("Район");
        propertyObjectAddress.setZone("Зона");
        propertyCommercialObject.setAddress(propertyObjectAddress);
        propertyCommercialObject.setRoomQuantity(3);
        propertyCommercialObject.setFloor(5);
        propertyCommercialObject.setPrice(100000);  // Примерная цена
        propertyCommercialObject.setRealtor(new Realtor());
        propertyCommercialObject.setAreaTotal(150);  // Примерная площадь
        when(propertySecondaryObjectService.findById(anyInt())).thenReturn(Optional.of(propertyCommercialObject));
        // Use a real list instead of any(List.class)
        List<BuyerApplication> buyerApplications = new ArrayList<>();
        when(buyerApplicationRepository.findAll(any(BuyerForObjectSpecification.class))).thenReturn(buyerApplications);
        // Act
        List<Buyer> result = buyerService.filterForObject(anyInt(), type);
        // Assert
        verify(buyerApplicationRepository, times(1)).findAll(any(BuyerForObjectSpecification.class));
        // Add additional assertions based on your specific implementation and requirements
    }

    @Test
    public void testFilterForObjectCOMMERCIAL() {
        // Arrange
        String type = "COMMERCIAL";
        PropertyCommercialObject propertyCommercialObject = new PropertyCommercialObject();
        propertyCommercialObject.setId(1);
        PropertyObjectAddress propertyObjectAddress=new PropertyObjectAddress();
        propertyObjectAddress.setStreet("Название улицы");
        propertyObjectAddress.setCity("Город");
        propertyObjectAddress.setDistrict("Район");
        propertyObjectAddress.setZone("Зона");
        propertyCommercialObject.setAddress(propertyObjectAddress);
        propertyCommercialObject.setRoomQuantity(3);
        propertyCommercialObject.setFloor(5);
        propertyCommercialObject.setPrice(100000);  // Примерная цена
        propertyCommercialObject.setRealtor(new Realtor());
        propertyCommercialObject.setAreaTotal(150);  // Примерная площадь
        when(commercialService.getById(anyInt())).thenReturn(propertyCommercialObject);
        // Use a real list instead of any(List.class)
        List<BuyerApplication> buyerApplications = new ArrayList<>();
        when(buyerApplicationRepository.findAll(any(BuyerForObjectSpecification.class))).thenReturn(buyerApplications);
        // Act
        List<Buyer> result = buyerService.filterForObject(anyInt(), type);
        // Assert
        verify(buyerApplicationRepository, times(1)).findAll(any(BuyerForObjectSpecification.class));
        // Add additional assertions based on your specific implementation and requirements
    }


}