package com.manideep.spendguide.service;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

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
    public void sendEmail(String sendTo, String subject, String body) throws RuntimeException {
        
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
    
    // Overload sendEmail method for sending attachment
    @Override
    public void sendEmail(
        String sendTo, String subject, String body, ByteArrayInputStream excelStream, String filename
        ) throws RuntimeException {
        
        // Sending email using MimeMessage along with MimeMessageHelper from spring-boot-starter-mail
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            // Setting the mutipart message as true for sending attachments
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(senderEmail);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(body);

            // Type casting ByteArrayInputStream to ByteArrayDataStream and then upcasting it to DataSource,
            // as DataSource is accepted as a parameter in addAttachment()
            DataSource dataSource = new ByteArrayDataSource(excelStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            helper.addAttachment(filename, dataSource);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }

}
