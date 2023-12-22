package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.example.entity.Banner;
import org.example.repository.BannerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

class BannerServiceTest {

    @Mock
    private BannerRepository bannerRepository;

    @InjectMocks
    private BannerService bannerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        Integer bannerId = 1;
        Banner mockBanner = new Banner();
        when(bannerRepository.findById(bannerId)).thenReturn(Optional.of(mockBanner));

        // Act
        Optional<Banner> result = bannerService.findById(bannerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockBanner, result.get());

        // Verify
        verify(bannerRepository, times(1)).findById(bannerId);
    }

    @Test
    void testSave() {
        // Arrange
        Banner bannerToSave = new Banner();

        // Act
        bannerService.save(bannerToSave);

        // Verify
        verify(bannerRepository, times(1)).save(bannerToSave);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Integer bannerId = 1;

        // Act
        bannerService.deleteById(bannerId);

        // Verify
        verify(bannerRepository, times(1)).deleteById(bannerId);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Banner> mockBannerList = Collections.singletonList(new Banner());
        when(bannerRepository.findAll()).thenReturn(mockBannerList);

        // Act
        List<Banner> result = bannerService.findAll();

        // Assert
        assertEquals(mockBannerList, result);

        // Verify
        verify(bannerRepository, times(1)).findAll();
    }
}