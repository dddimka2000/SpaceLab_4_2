package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.repository.RealtorContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class RealtorContactServiceImplTest {

    @Mock
    private RealtorContactRepository realtorContactRepository;

    @InjectMocks
    private RealtorContactServiceImpl realtorContactService;

    @Test
    void deleteById() {
        // Arrange
        int id = 1;

        // Act
        realtorContactService.deleteById(id);

        // Assert
        verify(realtorContactRepository, times(1)).deleteById(id);
    }
}