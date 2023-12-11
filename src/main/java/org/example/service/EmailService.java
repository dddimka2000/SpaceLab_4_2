package org.example.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.SecureRandom;

@Service
@Log4j2
public class EmailService {

    private final Environment env;

    public EmailService(Environment env) {
        this.env = env;
    }

    public void sendEmail(String to, String subject, String text) {
        log.info("EmailService-sendEmail start");

        SendGrid sendGrid = new SendGrid("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");
        Request request = new Request();

        Email from = new Email("tymur.foshch@avada-media.com");
        Email toEmail = new Email(to);

        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, toEmail, content);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            request.setBaseUri("https://api.sendgrid.net/");
            Response response = sendGrid.api(request);
            String responseBody = response.getBody();
            int statusCode = response.getStatusCode();
            if (statusCode == 202) {
                log.info("Email was successfully sent.");
                log.info("Response Body: {}", responseBody);
            } else {
                log.error("Error sending email.");
                log.error("Response Body: {}", responseBody);
            }
        } catch (IOException ex) {
            log.error("Exception occurred while sending email.", ex);
        }

        log.info("EmailService-sendEmail successfully");
    }

    public String generateRandomPassword(int minLength, int maxLength) {
        log.info("EmailService-generateRandomPassword start");

        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()_-+=<>?";

        String allCharacters = uppercaseLetters + lowercaseLetters + digits + specialCharacters;

        SecureRandom random = new SecureRandom();
        int passwordLength = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allCharacters.length());
            char randomChar = allCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        log.info("EmailService-generateRandomPassword successfully");
        return password.toString();
    }
}
