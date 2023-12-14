package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import groovy.util.logging.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Test
    public void testSendEmail() throws Exception {
        SendGrid sendGrid = mock(SendGrid.class);
        emailService.setSendGrid(sendGrid);
        // Arrange
        String toEmail = "2000-dimk@ukr.net";
        String subject = "Test Subject";
        String text = "Test Content";
        String to = "recipient@example.com";

        Request requestMock = Mockito.mock(Request.class);
        Response responseMock = new Response(202, "Accepted", new HashMap<>());

        Mockito.lenient().when(sendGrid.api(Mockito.any()))
                .thenReturn(responseMock);

        assertDoesNotThrow(() -> emailService.sendEmail(to, subject, text));

    }
    @Test
    public void testGenerateRandomPassword() {
        int minLength = 8;
        int maxLength = 12;

        String password = emailService.generateRandomPassword(minLength, maxLength);

        assertNotNull(password);
        assertTrue(password.length() >= minLength && password.length() <= maxLength);
    }
    @Test
    void testSendEmailFailure() throws Exception {
        SendGrid sendGrid = mock(SendGrid.class);
        emailService.setSendGrid(sendGrid);

        // Arrange
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "Test Content";

        Request requestMock = Mockito.mock(Request.class);
        Response responseMock = new Response(500, "Internal Server Error", new HashMap<>());

        when(sendGrid.api(any(Request.class))).thenThrow(new IOException("Simulated IO exception"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> emailService.sendEmail(to, subject, text));
    }
    @Test
    public void testSendEmail500() throws Exception {
        SendGrid sendGrid = mock(SendGrid.class);
        emailService.setSendGrid(sendGrid);
        // Arrange
        String toEmail = "2000-dimk@ukr.net";
        String subject = "Test Subject";
        String text = "Test Content";
        String to = "recipient@example.com";

        Request requestMock = Mockito.mock(Request.class);
        Response responseMock = new Response(500, "Internal Server Error", new HashMap<>());

        Mockito.lenient().when(sendGrid.api(Mockito.any()))
                .thenReturn(responseMock);

        assertDoesNotThrow(() -> emailService.sendEmail(to, subject, text));

    }
}