package org.example.service;

import org.example.entity.ImagesForObject;
import org.example.entity.property.type.TypeObject;

import static org.junit.jupiter.api.Assertions.*;

import org.example.repository.ImageForObjectsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ImagesForObjectServiceTest {

    @Mock
    private ImageForObjectsRepository imageForObjectsRepository;

    @InjectMocks
    private ImagesForObjectService imagesForObjectService;

    @Test
    void save() {
        // Arrange
        ImagesForObject imagesForObject = new ImagesForObject();

        // Act
        imagesForObjectService.save(imagesForObject);

        // Assert
        verify(imageForObjectsRepository, times(1)).save(imagesForObject);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        imagesForObjectService.deleteById(id);

        // Assert
        verify(imageForObjectsRepository, times(1)).deleteById(id);
    }

    @Test
    void findByIdObjectAndTypeObject() {
        // Arrange
        Integer id = 1;
        TypeObject typeObject = TypeObject.BY_BUILDER;
        List<ImagesForObject> expectedList = Collections.singletonList(new ImagesForObject());
        when(imageForObjectsRepository.findByIdObjectAndTypeObject(id, typeObject)).thenReturn(expectedList);

        // Act
        List<ImagesForObject> result = imagesForObjectService.findByIdObjectAndTypeObject(id, typeObject);

        // Assert
        assertEquals(expectedList, result);
        verify(imageForObjectsRepository, times(1)).findByIdObjectAndTypeObject(id, typeObject);
    }

    @Test
    void findOneByIdObjectAndTypeObject() {
        // Arrange
        Integer id = 1;
        TypeObject typeObject = TypeObject.BY_BUILDER;
        ImagesForObject expectedEntity = new ImagesForObject();
        when(imageForObjectsRepository.findFirstByIdObjectAndTypeObject(id, typeObject)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<ImagesForObject> result = imagesForObjectService.findOneByIdObjectAndTypeObject(id, typeObject);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(imageForObjectsRepository, times(1)).findFirstByIdObjectAndTypeObject(id, typeObject);
    }
}