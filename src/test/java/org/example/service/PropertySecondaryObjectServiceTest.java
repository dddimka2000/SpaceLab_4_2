package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.property.PropertySecondaryObject;
import org.example.repository.PropertySecondaryObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
}
