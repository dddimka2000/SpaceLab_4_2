package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.ExchangeRates;
import org.example.repository.ExchangeRatesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceTest {

    @Mock
    private ExchangeRatesRepository exchangeRatesRepository;

    @InjectMocks
    private ExchangeRatesService exchangeRatesService;

    @Test
    void findByName() {
        // Arrange
        String name = "USD";
        ExchangeRates expectedExchangeRates = new ExchangeRates();
        when(exchangeRatesRepository.findByName(name)).thenReturn(Optional.of(expectedExchangeRates));

        // Act
        Optional<ExchangeRates> result = exchangeRatesService.findByName(name);

        // Assert
        assertEquals(Optional.of(expectedExchangeRates), result);
        verify(exchangeRatesRepository, times(1)).findByName(name);
    }

    @Test
    void save() {
        // Arrange
        ExchangeRates exchangeRates = new ExchangeRates();

        // Act
        exchangeRatesService.save(exchangeRates);

        // Assert
        verify(exchangeRatesRepository, times(1)).save(exchangeRates);
    }
}
