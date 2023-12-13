package org.example.service;

import org.example.entity.property.PropertyInvestorObject;
import org.example.repository.PropertyInvestorObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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



}
