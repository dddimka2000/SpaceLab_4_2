package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import io.minio.errors.*;
import org.example.dto.RealtorDto;
import org.example.entity.Realtor;
import org.example.entity.RealtorContact;
import org.example.mapper.RealtorMapper;
import org.example.repository.RealtorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealtorServiceImplTest {

    @Mock
    private RealtorRepository realtorRepository;

    @Mock
    private RealtorMapper realtorMapper;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private RealtorServiceImpl realtorService;

    @Test
    void testDeleteById() {
        // Act
        assertDoesNotThrow(() -> realtorService.deleteById(1));

        // Verify interactions
        verify(realtorRepository).deleteById(1);
    }
    @Test
    void testAddNewRealtor() throws Exception {
        // Arrange
        RealtorDto realtorDto = new RealtorDto();
        realtorDto.setId(1);
        Realtor realtor = new Realtor();
        realtor.setId(1);
        realtor.setContacts(List.of(new RealtorContact()));
        when(realtorRepository.findById(anyInt())).thenReturn(Optional.of(realtor));
        doNothing().when(realtorMapper).updateEntityFromDto(any(RealtorDto.class), any(Realtor.class), any(MinioService.class));
        when(realtorRepository.save(any())).thenReturn(null);
        // Act
        assertDoesNotThrow(() -> realtorService.add(realtorDto));

        // Verify interactions
        verify(realtorMapper).updateEntityFromDto(any(), any(), any());
        verify(realtorMapper, never()).toEntity(realtorDto, minioService);
        verify(realtorRepository).save(realtor);
    }
    @Test
    void testAddNewRealtorWhereIdIsNull() throws Exception {
        // Arrange
        RealtorDto realtorDto = new RealtorDto();
        Realtor realtor = new Realtor();
        realtor.setContacts(List.of(new RealtorContact()));
        when(realtorMapper.toEntity(realtorDto, minioService)).thenReturn(realtor);
        when(realtorRepository.save(any())).thenReturn(null);
        // Act
        assertDoesNotThrow(() -> realtorService.add(realtorDto));

        // Verify interactions
        verify(realtorMapper).toEntity(realtorDto, minioService);
        verify(realtorMapper, never()).updateEntityFromDto(any(), any(), any());
        verify(realtorRepository).save(realtor);
    }


    @Test
    void testGetAll() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(realtorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Act
        Page<Realtor> result = realtorService.getAll(0, "branchId", "code", "fullName", "phone", "email", "birthdate");

        // Assert
        assertNotNull(result);
        verify(realtorRepository).findAll(any(Specification.class),  any(Pageable.class));
    }

    @Test
    void testGetById() {
        // Arrange
        when(realtorRepository.findById(1)).thenReturn(Optional.of(new Realtor()));

        // Act
        Realtor result = realtorService.getById(1);

        // Assert
        assertNotNull(result);
        verify(realtorRepository).findById(1);
    }


    @Test
    void testSave() {
        // Arrange
        Realtor realtor = new Realtor();

        // Act
        assertDoesNotThrow(() -> realtorService.save(realtor));

        // Verify interactions
        verify(realtorRepository).save(realtor);
    }

    @Test
    void testForSelect() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(realtorRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Act
        Page<Realtor> result = realtorService.forSelect("name", pageable);

        // Assert
        assertNotNull(result);
        verify(realtorRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testCountByCode() {
        // Arrange
        when(realtorRepository.countByCode(123)).thenReturn(1);

        // Act
        int result = realtorService.countByCode(123);

        // Assert
        assertEquals(1, result);
        verify(realtorRepository).countByCode(123);
    }

    @Test
    void testGetByCode() {
        // Arrange
        when(realtorRepository.findByCode(456)).thenReturn(new Realtor());

        // Act
        Realtor result = realtorService.getByCode(456);

        // Assert
        assertNotNull(result);
        verify(realtorRepository).findByCode(456);
    }


}
