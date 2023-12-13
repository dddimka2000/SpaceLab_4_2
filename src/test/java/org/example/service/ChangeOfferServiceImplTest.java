package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.ChangeOffer;
import org.example.repository.ChangeOfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeOfferServiceImplTest {

    @Mock
    private ChangeOfferRepository changeOfferRepository;

    @InjectMocks
    private ChangeOfferServiceImpl changeOfferService;

    @Test
    void save() {
        // Arrange
        ChangeOffer changeOffer = new ChangeOffer();

        // Act
        changeOfferService.save(changeOffer);

        // Assert
        verify(changeOfferRepository, times(1)).save(changeOffer);
    }

    @Test
    void getAll() {
        // Arrange
        List<ChangeOffer> changeOfferList = new ArrayList<>();
        Page<ChangeOffer> expectedPage = new PageImpl<>(changeOfferList);
        when(changeOfferRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<ChangeOffer> result = changeOfferService.getAll(0);

        // Assert
        assertEquals(expectedPage, result);
        verify(changeOfferRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getById() {
        // Arrange
        Integer id = 1;
        ChangeOffer expectedChangeOffer = new ChangeOffer();
        when(changeOfferRepository.findById(id)).thenReturn(Optional.of(expectedChangeOffer));

        // Act
        ChangeOffer result = changeOfferService.getById(id);

        // Assert
        assertEquals(expectedChangeOffer, result);
        verify(changeOfferRepository, times(1)).findById(id);
    }

    @Test
    void count() {
        // Arrange
        long expectedCount = 10L;
        when(changeOfferRepository.count()).thenReturn(expectedCount);

        // Act
        long result = changeOfferService.count();

        // Assert
        assertEquals(expectedCount, result);
        verify(changeOfferRepository, times(1)).count();
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        changeOfferService.deleteById(id);

        // Assert
        verify(changeOfferRepository, times(1)).deleteById(id);
    }
}