package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.BuilderObject;
import org.example.repository.BuilderObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ObjectBuilderServiceTest {

    @Mock
    private BuilderObjectRepository builderObjectRepository;

    @InjectMocks
    private ObjectBuilderService objectBuilderService;

    @Test
    void count() {
        // Arrange
        when(builderObjectRepository.count()).thenReturn(10L);

        // Act
        long result = objectBuilderService.count();

        // Assert
        assertEquals(10L, result);
        verify(builderObjectRepository, times(1)).count();
    }

    @Test
    void findById() {
        // Arrange
        Integer id = 1;
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findById(id);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findById(id);
    }



    @Test
    void findByName() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByName(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByName(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByName(name);
    }

    @Test
    void findByNameEnglish() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByNameEnglish(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByNameEnglish(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByNameEnglish(name);
    }

    @Test
    void findByNameUkraine() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByNameUkraine(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByNameUkraine(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByNameUkraine(name);
    }

    @Test
    void save() {
        // Arrange
        BuilderObject entity = new BuilderObject();

        // Act
        objectBuilderService.save(entity);

        // Assert
        verify(builderObjectRepository, times(1)).save(entity);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;
        BuilderObject builderObject = new BuilderObject();
        when(builderObjectRepository.findById(id)).thenReturn(Optional.of(builderObject));

        // Act
        objectBuilderService.deleteById(id);

        // Assert
        verify(builderObjectRepository, times(1)).delete(builderObject);
    }
    @Test
    void testFindBuilderObjectsByCriteria() {
        // Arrange
        String name = "BuilderObjectName";
        String district = "DistrictName";
        String zone = "ZoneName";
        String street = "StreetName";
        Integer floorQuantity = 5;
        Integer minPrice = 100000;
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.findBuilderObjectsByCriteria(
                name, district, zone, street, floorQuantity, minPrice, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testFindBuilderObjectsPage() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.findBuilderObjectsPage(pageNumber, pageSize);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testForSelect() {
        // Arrange
        String name = "BuilderObjectName";
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.forSelect(name, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }



}