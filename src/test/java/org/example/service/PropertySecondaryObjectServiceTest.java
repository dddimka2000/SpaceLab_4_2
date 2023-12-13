package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.property.PropertySecondaryObject;
import org.example.repository.PropertySecondaryObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertySecondaryObjectServiceTest {

    @Mock
    private PropertySecondaryObjectRepository propertySecondaryObjectRepository;

    @InjectMocks
    private PropertySecondaryObjectService propertySecondaryObjectService;

    @Test
    void save() {
        // Arrange
        PropertySecondaryObject entity = new PropertySecondaryObject();

        // Act
        propertySecondaryObjectService.save(entity);

        // Assert
        verify(propertySecondaryObjectRepository, times(1)).save(entity);
    }

    @Test
    void count() {
        // Arrange
        when(propertySecondaryObjectRepository.count()).thenReturn(10L);

        // Act
        long result = propertySecondaryObjectService.count();

        // Assert
        assertEquals(10L, result);
        verify(propertySecondaryObjectRepository, times(1)).count();
    }

    @Test
    void findByCode() {
        // Arrange
        String code = "ABC123";
        PropertySecondaryObject expectedEntity = new PropertySecondaryObject();
        when(propertySecondaryObjectRepository.findByObjectCode(code)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PropertySecondaryObject> result = propertySecondaryObjectService.findByCode(code);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(propertySecondaryObjectRepository, times(1)).findByObjectCode(code);
    }

    @Test
    void findById() {
        // Arrange
        Integer id = 1;
        PropertySecondaryObject expectedEntity = new PropertySecondaryObject();
        when(propertySecondaryObjectRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PropertySecondaryObject> result = propertySecondaryObjectService.findById(id);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(propertySecondaryObjectRepository, times(1)).findById(id);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        propertySecondaryObjectService.deleteById(id);

        // Assert
        verify(propertySecondaryObjectRepository, times(1)).deleteById(id);
    }
    @Test
    void testForSelect() {
        // Arrange
        String name = "SecondaryObjectName";
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<PropertySecondaryObject> secondaryObjects = new ArrayList<>();
        Page<PropertySecondaryObject> expectedPage = new PageImpl<>(secondaryObjects);

        when(propertySecondaryObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<PropertySecondaryObject> result = propertySecondaryObjectService.forSelect(name, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(propertySecondaryObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
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
        List<PropertySecondaryObject> secondaryObjects = new ArrayList<>();
        Page<PropertySecondaryObject> expectedPage = new PageImpl<>(secondaryObjects);

        when(propertySecondaryObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<PropertySecondaryObject> result = propertySecondaryObjectService.findBuilderObjectsByCriteria(
                district, numberRooms, minFloor, maxFloor, minPrice, maxPrice, topozone, residentialComplexId,
                minNumberFloors, maxNumberFloors, minArea, maxArea, street, lastContactDate, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(propertySecondaryObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
