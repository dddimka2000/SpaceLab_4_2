package org.example.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.SecureRandom;

@Service
@Log4j2
@Setter
public class EmailService {
    private SendGrid sendGrid= new SendGrid(System.getenv("MY_SECRET_KEY"));
    public void sendEmail(String to, String subject, String text) {
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
                System.out.println("Email was sent successfully");
                System.out.println("Response Body: " + responseBody);
            } else {
                System.out.println("Error - email send");
            }
        } catch (IOException ex) {
            log.error("Error sending email.", ex);
            throw new RuntimeException("Error sending email.", ex); // or handle it appropriately
        }
    }
    public String generateRandomPassword(int minLength, int maxLength) {
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
        return password.toString();
    }
}
