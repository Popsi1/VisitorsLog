package com.example.logs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("umeigboobumneme@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println(message);
    }
}
