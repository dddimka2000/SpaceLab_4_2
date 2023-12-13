package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.repository.AddressExelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExelServiceTest {

    @Mock
    private AddressExelRepository addressRepository;

    @InjectMocks
    private ExelService exelService;

    @Test
    void checkStreetExistWhenStreetExists() {
        // Arrange
        String streetName = "Main Street";
        when(addressRepository.existsByStreetName(streetName)).thenReturn(true);

        // Act
        Boolean result = exelService.checkStreetExist(streetName);

        // Assert
        assertEquals(true, result);
        verify(addressRepository, times(1)).existsByStreetName(streetName);
    }

    @Test
    void checkStreetExistWhenStreetDoesNotExist() {
        // Arrange
        String streetName = "Non-existent Street";
        when(addressRepository.existsByStreetName(streetName)).thenReturn(false);

        // Act
        Boolean result = exelService.checkStreetExist(streetName);

        // Assert
        assertEquals(false, result);
        verify(addressRepository, times(1)).existsByStreetName(streetName);
    }
}
