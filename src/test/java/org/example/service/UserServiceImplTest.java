package org.example.service;

import io.minio.errors.*;
import org.example.dto.UserDto;
import org.example.entity.UserEntity;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

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

    @Test
    void add() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserDto userDto = new UserDto();
        userDto.setId(1);

        UserEntity mockUser = new UserEntity();
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(mockUser));
        lenient().when(userMapper.toEntity(userDto)).thenReturn(mockUser);

        userService.add(userDto);
    }
    @Test
    void addWhereUsersIdIsNull() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UserDto userDto = new UserDto();

        UserEntity mockUser = new UserEntity();
        lenient().when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(mockUser));
        lenient().when(userMapper.toEntity(userDto)).thenReturn(mockUser);

        userService.add(userDto);
    }

    @Test
    void getAll() {
        int page = 0;
        String role = "ADMIN";
        String surname = "Doe";
        String name = "John";
        String middleName = "Middle";
        String phone = "123456789";
        String email = "john.doe@example.com";

        Page<UserEntity> mockPage = Mockito.mock(Page.class);

        Mockito.when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Page<UserEntity> result = userService.getAll(page, role, surname, name, middleName, phone, email);

        assertEquals(mockPage, result);
    }


    @Test
    public void testGetAuthUser() {
        // Arrange
        UserEntity expectedUser = new UserEntity(); // Создайте экземпляр UserEntity, который вы ожидаете получить
        when(userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        // Act
        UserEntity actualUser = userService.getAuthUser();

        // Assert
        // Проверьте, что метод findById вызывается с ожидаемым параметром
        verify(userRepository, times(1)).findById(1);
        // Проверьте, что возвращенный пользователь соответствует ожидаемому пользователю
        assertEquals(expectedUser, actualUser);
    }
}
