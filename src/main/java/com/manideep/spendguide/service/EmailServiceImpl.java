package com.manideep.spendguide.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    // static because only one instance of Logger is needed for this class and final to avoid accidental changes.
    // private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    @Override
    public void sendEmail(String sendTo, String subject, String body) {
        
        // Sending email using SimpleMailMessage from spring-boot-starter-mail
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(senderEmail);
            email.setTo(sendTo);
            email.setSubject(subject);
            email.setText(body);
            javaMailSender.send(email);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }

}
