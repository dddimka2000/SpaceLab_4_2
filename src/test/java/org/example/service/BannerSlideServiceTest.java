package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import io.minio.errors.*;
import io.minio.messages.ErrorResponse;
import lombok.SneakyThrows;
import org.example.dto.BannerDto;
import org.example.dto.BannerSlideDto;
import org.example.entity.Banner;
import org.example.entity.BannerSlide;
import org.example.repository.BannerRepository;
import org.example.repository.BannerSlideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BannerSlideServiceTest {
    @Mock
    private BannerSlideRepository bannerSlideRepository;
    @Mock
    private BannerRepository bannerRepository;
    @Mock
    private BannerService bannerService;
    @Mock
    private MinioService minioService;
    @InjectMocks
    private BannerSlideService bannerSlideService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @SneakyThrows
    @Test
    public void testSaveEdit() {
        // Arrange
        Banner existingBanner = new Banner(); // Создайте существующий баннер для теста
        List<BannerSlide> existingSlides = new ArrayList<>();
        existingSlides.add(new BannerSlide());
        existingBanner.setSlides(existingSlides);
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        BannerDto bannerDto = new BannerDto(); // Создайте объект BannerDto для теста
        BannerSlideDto bannerSlide=new BannerSlideDto();
        bannerSlide.setImgPath(multipartFile);
        List<BannerSlideDto> list=new ArrayList<>();
        list.add(bannerSlide);
        bannerDto.setSlides(list);
        List<BannerSlideDto> newSlides = new ArrayList<>();
        BannerSlideDto newSlideDto = new BannerSlideDto();
        newSlideDto.setOldImgPath("old/path");
        newSlides.add(newSlideDto);

        Optional<Banner> optionalBanner = Optional.of(existingBanner);
        when(bannerRepository.findById(any())).thenReturn(optionalBanner);

        // Act
        bannerSlideService.saveEdit(optionalBanner, bannerDto);
    }
    @SneakyThrows
    @Test
    public void testSaveEditNullIf() {
        // Arrange
        Banner existingBanner = new Banner(); // Создайте существующий баннер для теста
        List<BannerSlide> existingSlides = new ArrayList<>();
        existingSlides.add(new BannerSlide());
        existingBanner.setSlides(existingSlides);
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        BannerDto bannerDto = new BannerDto();
        BannerSlideDto bannerSlide=new BannerSlideDto();
        bannerSlide.setImgPath(multipartFile);
        bannerSlide.setOldImgPath("wrrw");

        List<BannerSlideDto> list=new ArrayList<>();
        list.add(bannerSlide);
        bannerDto.setSlides(list);

        Optional<Banner> optionalBanner = Optional.of(existingBanner);
        when(bannerRepository.findById(any())).thenReturn(optionalBanner);

        // Act
        bannerSlideService.saveEdit(optionalBanner, bannerDto);
    }
    @SneakyThrows
    @Test
    void testSaveEditHandlesError() {
        // Создайте mock для MinioService
        Banner existingBanner = new Banner(); // Создайте существующий баннер для теста
        List<BannerSlide> existingSlides = new ArrayList<>();
        existingSlides.add(new BannerSlide());
        existingBanner.setSlides(existingSlides);
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        BannerDto bannerDto = new BannerDto();
        BannerSlideDto bannerSlide=new BannerSlideDto();
        bannerSlide.setImgPath(multipartFile);
        bannerSlide.setOldImgPath("wrrw");

        List<BannerSlideDto> list=new ArrayList<>();
        list.add(bannerSlide);
        bannerDto.setSlides(list);

        Optional<Banner> optionalBanner = Optional.of(existingBanner);
        when(bannerRepository.findById(any())).thenReturn(optionalBanner);

        // Act
        // Установите mock так, чтобы выбросить исключение при вызове putMultipartFile
        doThrow(new RuntimeException()).when(minioService).putMultipartFile(any(), any(), any());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bannerSlideService.saveEdit(Optional.of(new Banner()), bannerDto);
            // убедитесь, что метод putMultipartFile был вызван ожидаемое количество раз
            verify(minioService, times(1)).putMultipartFile(any(), any(), any());
        });
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