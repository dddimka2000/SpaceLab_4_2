package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        String username = "test@example.com";
        UserEntity userEntity = new UserEntity(/* provide necessary parameters */);
        userEntity.setEmail(username);
        userEntity.setName(username);
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        // Add more assertions based on your UserDetailsImpl implementation
        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    public void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        // Arrange
        String username = "nonexistent@example.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
    @Test
    void save_UserEntityProvided_ShouldCallRepositorySave() {
        // Arrange
        UserEntity userEntity = new UserEntity(/* provide necessary parameters */);

        // Act
        userDetailsService.save(userEntity);

        // Assert
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void findByEmail_EmailProvided_UserEntityOptionalReturned() {
        // Arrange
        String email = "test@example.com";
        UserEntity userEntity = new UserEntity(/* provide necessary parameters */);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // Act
        Optional<UserEntity> result = userDetailsService.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_NonexistentEmailProvided_EmptyOptionalReturned() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userDetailsService.findByEmail(email);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail(email);
    }

    // Add tests for save and findByEmail methods if needed
}
