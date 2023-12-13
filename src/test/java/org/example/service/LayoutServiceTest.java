package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.BuilderObject;
import org.example.entity.Layout;
import org.example.repository.LayoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LayoutServiceTest {

    @Mock
    private LayoutRepository layoutRepository;

    @InjectMocks
    private LayoutService layoutService;

    @Test
    void findByBuilderObject() {
        // Arrange
        BuilderObject builderObject = new BuilderObject();
        List<Layout> expectedList = Collections.singletonList(new Layout());
        when(layoutRepository.findByBuilderObject(builderObject)).thenReturn(expectedList);

        // Act
        List<Layout> result = layoutService.findByBuilderObject(builderObject);

        // Assert
        assertEquals(expectedList, result);
        verify(layoutRepository, times(1)).findByBuilderObject(builderObject);
    }

    @Test
    void save() {
        // Arrange
        Layout layout = new Layout();

        // Act
        layoutService.save(layout);

        // Assert
        verify(layoutRepository, times(1)).save(layout);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        layoutService.deleteById(id);

        // Assert
        verify(layoutRepository, times(1)).deleteById(id);
    }

    @Test
    void findLayoutsPage() {
        // Arrange
        BuilderObject builderObject = new BuilderObject();
        List<Layout> expectedList = Collections.singletonList(new Layout());
        when(layoutRepository.findByBuilderObject(builderObject)).thenReturn(expectedList);

        // Act
        List<Layout> result = layoutService.findLayoutsPage(builderObject);

        // Assert
        assertEquals(expectedList, result);
        verify(layoutRepository, times(1)).findByBuilderObject(builderObject);
    }

    @Test
    void deleteAllByBuilderObject() {
        // Arrange
        BuilderObject builderObject = new BuilderObject();

        // Act
        layoutService.deleteAllByBuilderObject(builderObject);

        // Assert
        verify(layoutRepository, times(1)).deleteAllByBuilderObject(builderObject);
    }
}
