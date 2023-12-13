package org.example.service;

import org.example.entity.property.PropertyInvestorObject;
import org.example.repository.PropertyInvestorObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyInvestorObjectServiceTest {

    @Mock
    private PropertyInvestorObjectRepository propertyInvestorObjectRepository;

    @InjectMocks
    private PropertyInvestorObjectService propertyInvestorObjectService;

    @Test
    void save() {
        // Arrange
        PropertyInvestorObject entity = new PropertyInvestorObject();

        // Act
        propertyInvestorObjectService.save(entity);

        // Assert
        verify(propertyInvestorObjectRepository, times(1)).save(entity);
    }

    @Test
    void findByCode() {
        // Arrange
        String code = "ABC123";
        PropertyInvestorObject expectedEntity = new PropertyInvestorObject();
        when(propertyInvestorObjectRepository.findByObjectCode(code)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PropertyInvestorObject> result = propertyInvestorObjectService.findByCode(code);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(propertyInvestorObjectRepository, times(1)).findByObjectCode(code);
    }

    @Test
    void findById() {
        // Arrange
        Integer id = 1;
        PropertyInvestorObject expectedEntity = new PropertyInvestorObject();
        when(propertyInvestorObjectRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PropertyInvestorObject> result = propertyInvestorObjectService.findById(id);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(propertyInvestorObjectRepository, times(1)).findById(id);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        propertyInvestorObjectService.deleteById(id);

        // Assert
        verify(propertyInvestorObjectRepository, times(1)).deleteById(id);
    }

    @Test
    void count() {
        // Arrange
        when(propertyInvestorObjectRepository.count()).thenReturn(10L);

        // Act
        long result = propertyInvestorObjectService.count();

        // Assert
        assertEquals(10L, result);
        verify(propertyInvestorObjectRepository, times(1)).count();
    }
    @Test
    void testForSelect() {
        // Arrange
        String name = "InvestorName";
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<PropertyInvestorObject> investorObjects = new ArrayList<>();
        Page<PropertyInvestorObject> expectedPage = new PageImpl<>(investorObjects);

        when(propertyInvestorObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<PropertyInvestorObject> result = propertyInvestorObjectService.forSelect(name, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(propertyInvestorObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testFindBuilderObjectsByCriteria() {
        // Arrange
        List<String> district = new ArrayList<>();
        Integer numberRooms = 2;
        Integer minFloor = 1;
        Integer maxFloor = 5;
        Integer minPrice = 100000;
        Integer maxPrice = 500000;
        List<String> topozone = new ArrayList<>();
        List<Integer> residentialComplexId = new ArrayList<>();
        Integer minNumberFloors = 1;
        Integer maxNumberFloors = 10;
        Integer minArea = 50;
        Integer maxArea = 200;
        String street = "Main Street";
        LocalDate lastContactDate = LocalDate.now();
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<PropertyInvestorObject> investorObjects = new ArrayList<>();
        Page<PropertyInvestorObject> expectedPage = new PageImpl<>(investorObjects);

        when(propertyInvestorObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<PropertyInvestorObject> result = propertyInvestorObjectService.findBuilderObjectsByCriteria(
                district, numberRooms, minFloor, maxFloor, minPrice, maxPrice, topozone, residentialComplexId,
                minNumberFloors, maxNumberFloors, minArea, maxArea, street, lastContactDate, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(propertyInvestorObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }


}
