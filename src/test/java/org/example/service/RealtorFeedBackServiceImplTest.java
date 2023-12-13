package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.RealtorFeedBack;
import org.example.repository.RealtorFeedBackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealtorFeedBackServiceImplTest {

    @Mock
    private RealtorFeedBackRepository realtorFeedBackRepository;

    @InjectMocks
    private RealtorFeedBackServiceImpl realtorFeedBackService;

    @Test
    void deleteById() {
        // Arrange
        int id = 1;

        // Act
        realtorFeedBackService.deleteById(id);

        // Assert
        verify(realtorFeedBackRepository, times(1)).deleteById(id);
    }

    @Test
    @Transactional
    void save() {
        // Arrange
        RealtorFeedBack realtorFeedBack = new RealtorFeedBack();

        // Act
        realtorFeedBackService.save(realtorFeedBack);

        // Assert
        verify(realtorFeedBackRepository, times(1)).save(realtorFeedBack);
    }

    @Test
    void getAll() {
        // Arrange
        RealtorFeedBack feedback1 = new RealtorFeedBack();
        RealtorFeedBack feedback2 = new RealtorFeedBack();
        when(realtorFeedBackRepository.findAll()).thenReturn(Arrays.asList(feedback1, feedback2));

        // Act
        List<RealtorFeedBack> result = realtorFeedBackService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(realtorFeedBackRepository, times(1)).findAll();
    }
}
