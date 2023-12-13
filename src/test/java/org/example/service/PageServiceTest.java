package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.PageEntity;
import org.example.repository.PageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageServiceTest {

    @Mock
    private PageRepository pageRepository;

    @InjectMocks
    private PageService pageService;

    @Test
    void findById() {
        // Arrange
        Integer id = 1;
        PageEntity expectedEntity = new PageEntity();
        when(pageRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<PageEntity> result = pageService.findById(id);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(pageRepository, times(1)).findById(id);
    }

    @Test
    void save() {
        // Arrange
        PageEntity entity = new PageEntity();

        // Act
        pageService.save(entity);

        // Assert
        verify(pageRepository, times(1)).save(entity);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;

        // Act
        pageService.deleteById(id);

        // Assert
        verify(pageRepository, times(1)).deleteById(id);
    }

    @Test
    void findAll() {
        // Arrange
        List<PageEntity> expectedList = Collections.singletonList(new PageEntity());
        when(pageRepository.findAll()).thenReturn(expectedList);

        // Act
        List<PageEntity> result = pageService.findAll();

        // Assert
        assertEquals(expectedList, result);
        verify(pageRepository, times(1)).findAll();
    }
}