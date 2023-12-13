package org.example.service;

import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MinioService minioService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById_ValidUserId_ReturnsUserEntity() {
        // Arrange
        int userId = 1;
        UserEntity expectedUser = new UserEntity(/* provide necessary parameters */);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(expectedUser));

        // Act
        UserEntity result = userService.getById(userId);

        // Assert
        assertEquals(expectedUser, result);
    }



    @Test
    void save_UserEntity_CallsUserRepositorySave() {
        // Arrange
        UserEntity userEntity = new UserEntity(/* provide necessary parameters */);

        // Act
        userService.save(userEntity);

        // Assert
        verify(userRepository, times(1)).save(userEntity);
    }


    @Test
    void deleteById_ValidUserId_CallsUserRepositoryDeleteById() {
        // Arrange
        int userId = 1;

        // Act
        userService.deleteById(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}
