package org.example.service;

import org.example.dto.RecaptchaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecaptchaServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecaptchaService recaptchaService;

    @Test
    void validateToken_shouldReturnRecaptchaResponse() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        String recaptchaToken = "your-recaptcha-token";
        RecaptchaResponse expectedResponse = new RecaptchaResponse(true, "timestamp", "hostname", 0.9, "action");
        Field secretKeyField = RecaptchaService.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(recaptchaService, "your-secret-key");

        Field verifyUrlField = RecaptchaService.class.getDeclaredField("verifyUrl");
        verifyUrlField.setAccessible(true);
        verifyUrlField.set(recaptchaService, "your-verify-url");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(RecaptchaResponse.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        // Act
        RecaptchaResponse actualResponse = recaptchaService.validateToken(recaptchaToken);

        // Assert
        verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), any(), eq(RecaptchaResponse.class));
        assertEquals(expectedResponse, actualResponse);
    }
}
