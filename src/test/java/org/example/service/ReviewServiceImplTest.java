package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.UserReview;
import org.example.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void deleteById() {
        // Arrange
        int reviewId = 1;

        // Act
        reviewService.deleteById(reviewId);

        // Assert
        verify(reviewRepository, times(1)).deleteById(reviewId);
        // Add any additional assertions based on your requirements
    }

    @Test
    void save() {
        // Arrange
        UserReview userReview = new UserReview(/* provide necessary parameters */);

        // Act
        reviewService.save(userReview);

        // Assert
        verify(reviewRepository, times(1)).save(userReview);
        // Add any additional assertions based on your requirements
    }
}
