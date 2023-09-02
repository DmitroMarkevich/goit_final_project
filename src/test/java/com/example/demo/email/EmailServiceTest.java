package com.example.demo.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

public class EmailServiceTest {
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(mailSender);
    }

    @Test
    public void testSendEmail() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(body);
        emailService.sendEmail(to, subject, body);
        verify(mailSender, Mockito.times(1)).send(expectedMessage);
    }
}
