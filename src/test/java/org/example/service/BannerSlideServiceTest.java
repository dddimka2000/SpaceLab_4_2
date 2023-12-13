package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import lombok.SneakyThrows;
import org.example.dto.BannerSlideDto;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.example.repository.BannerSlideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BannerSlideServiceTest {
    @Mock
    private BannerSlideRepository bannerSlideRepository;
    @Mock
    private MinioService minioService;
    @InjectMocks
    private BannerSlideService bannerSlideService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        BannerSlide bannerSlideToSave = new BannerSlide();

        // Act
        bannerSlideService.save(bannerSlideToSave);

        // Verify
        verify(bannerSlideRepository, times(1)).save(bannerSlideToSave);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Integer bannerSlideId = 1;

        // Act
        bannerSlideService.deleteById(bannerSlideId);

        // Verify
        verify(bannerSlideRepository, times(1)).deleteById(bannerSlideId);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<BannerSlide> mockBannerSlideList = new ArrayList<>();
        when(bannerSlideRepository.findAll()).thenReturn(mockBannerSlideList);

        // Act
        List<BannerSlide> result = bannerSlideService.findAll();

        // Assert
        assertEquals(mockBannerSlideList, result);

        // Verify
        verify(bannerSlideRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAllByBannerId() {
        // Arrange
        Integer bannerId = 1;

        // Act
        bannerSlideService.deleteAllByBannerId(bannerId);

        // Verify
        verify(bannerSlideRepository, times(1)).deleteAllByBannerId(bannerId);
    }

    @Test
    void testFindAllByBannerIdDTO() throws Exception {
        // Arrange
        List<BannerSlide> mockBannerSlideList = new ArrayList<>();
        mockBannerSlideList.add(new BannerSlide());

        when(minioService.getFileInString(anyString(), anyString())).thenReturn("mockedPath");

        // Act
        List<BannerSlideDto> result = bannerSlideService.findAllByBannerIdDTO(mockBannerSlideList);

        // Assert
        assertEquals(mockBannerSlideList.size(), result.size());

        // Verify
        for (BannerSlideDto bannerDto : result) {
            verify(minioService, times(1)).getFileInString(eq(bannerDto.getOldImgPath()), anyString());
        }
    }

    @Test
    void testFindAllByBannerIdDTOError() {
        List<BannerSlide> mockBannerSlideList = new ArrayList<>();
        mockBannerSlideList.add(new BannerSlide());
        when(bannerSlideService.findAllByBannerIdDTO(mockBannerSlideList)).thenThrow(ErrorResponseException.class);
        try {
            when(minioService.getFileInString(anyString(), anyString())).thenThrow(ErrorResponseException.class);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException | IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class, () -> bannerSlideService.findAllByBannerIdDTO(mockBannerSlideList));
    }


    @Test
    void testFindAllByBanner() {
        // Arrange
        Banner mockBanner = new Banner();
        List<BannerSlide> mockBannerSlideList = new ArrayList<>();
        when(bannerSlideRepository.findByBanner(mockBanner)).thenReturn(mockBannerSlideList);

        // Act
        List<BannerSlide> result = bannerSlideService.findAllByBanner(mockBanner);

        // Assert
        assertEquals(mockBannerSlideList, result);

        // Verify
        verify(bannerSlideRepository, times(1)).findByBanner(mockBanner);
    }


}