package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.dto.RecaptchaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecaptchaServiceTest {

    @InjectMocks
    private RecaptchaService recaptchaService;

    @Mock
    private RestTemplate restTemplate;
    @Value("${recaptcha.secretKey}")
    private String secretKey;
    @Value("${recaptcha.verifyUrl}")
    private String verifyUrl;
    @Test
    void testValidateToken() {
        //TODO не получился тест

        // Arrange
        String recaptchaToken = "sampleToken";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", recaptchaToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        RecaptchaResponse expectedResponse = new RecaptchaResponse(true, "sampleChallengeTs", "sampleHostname", 0.5, "sampleAction");

        // Use anyString() for the URL to avoid strict stubbing argument mismatch

        when(recaptchaService.validateToken(recaptchaToken)).thenReturn(expectedResponse);

        when(restTemplate.exchange(recaptchaToken, eq(HttpMethod.POST), any(HttpEntity.class), eq(RecaptchaResponse.class)))
                .thenReturn(ResponseEntity.ok().body(expectedResponse));
        // Act
        RecaptchaResponse result = recaptchaService.validateToken(recaptchaToken);

        // Assert
        assertEquals(expectedResponse, result);
    }

}
