package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.BuilderObject;
import org.example.repository.BuilderObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
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



}