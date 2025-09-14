package com.manideep.spendguide.service;

import java.io.ByteArrayInputStream;
// import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailAttachment;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import jakarta.activation.DataSource;
// import jakarta.mail.internet.MimeMessage;
// import jakarta.mail.util.ByteArrayDataSource;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    // This is Brevo API's client class for sending transaction emails 
    private final TransactionalEmailsApi emailsApi;

    @Value("${brevo.sender}")
    private String senderEmail;

    @Value("${brevo.senderName}")
    private String senderName;

    public EmailServiceImpl(@Value("${brevo.apiKey}") String apiKey) {

        // Configure the API client with the API key for authentication
        ApiClient defaultApiClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultApiClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);

        // Initialised for sending email
        this.emailsApi = new TransactionalEmailsApi();
    }

    @Override
    public void sendEmail(String sendTo, String subject, String body) throws RuntimeException {
        
        try {
            SendSmtpEmail email = new SendSmtpEmail()
                            .addToItem(new SendSmtpEmailTo().email(sendTo))
                            .subject(subject)
                            .sender(new SendSmtpEmailSender().email(senderEmail).name(senderName))
                            .htmlContent(body);

            emailsApi.sendTransacEmail(email); // This sends request to Brevo API

        } catch (RuntimeException | ApiException e) {
            logger.error("[SendSmtpEmail, TransactionalEmailsApi] failed to send email ", e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void sendEmail(
        String sendTo, String subject, String body, ByteArrayInputStream excelStream, String filename
    )throws RuntimeException {

        try {
            // Need to read the ByteArrayInputStream and store it in an array of bytes
            byte fileInBytes[] = excelStream.readAllBytes();
            // String base64File = Base64.getEncoder().encodeToString(fileInBytes);

            SendSmtpEmailAttachment excelAttachment = new SendSmtpEmailAttachment()
                                .name(filename)
                                .content(fileInBytes); // This method needs an array of bytes

            SendSmtpEmail email = new SendSmtpEmail()
                                .addToItem(new SendSmtpEmailTo().email(sendTo))
                                .subject(subject)
                                .sender(new SendSmtpEmailSender().email(senderEmail).name(senderName))
                                .htmlContent(body)
                                .addAttachmentItem(excelAttachment);

            emailsApi.sendTransacEmail(email); // This sends request to Brevo API

        } catch (RuntimeException | ApiException e) {
            logger.error("[SendSmtpEmailAttachment, TransactionalEmailsApi] failed to send email & excel ", e);
            throw new RuntimeException(e.getMessage());
        }

    }

}



// -------------------------------------------------------------------------------------------
/* 
 * BELOW CODE CAN'T BE USED IN PRODUCTION
 * as the port 587 for Brevo email sending service is blocked by Render free instance 
 * after there recent policy change.
 * 
 * Now I am switching from JavaMailServer to Brevo's API call for email sending
*/ 
// -------------------------------------------------------------------------------------------


// @Service
// public class EmailServiceImpl implements EmailService {

//     private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

//     private final JavaMailSender javaMailSender;

//     // static because only one instance of Logger is needed for this class and final to avoid accidental changes.
//     // private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

//     @Value("${spring.mail.properties.mail.smtp.from}")
//     private String senderEmail;

//     public EmailServiceImpl(JavaMailSender javaMailSender) {
//         this.javaMailSender = javaMailSender;
//     }
    
//     @Override
//     public void sendEmail(String sendTo, String subject, String body) throws RuntimeException {
        
//         // Sending email using SimpleMailMessage from spring-boot-starter-mail
//         try {
//             SimpleMailMessage email = new SimpleMailMessage();

//             email.setFrom(senderEmail);
//             email.setTo(sendTo);
//             email.setSubject(subject);
//             email.setText(body);
//             javaMailSender.send(email);
//         } catch (Exception e) {
//             logger.error("[SimpleMailMessage, JavaMailSender] failed to send email", e);
//             throw new RuntimeException(e.getMessage());
//         }
        
//     }
    
//     // Overload sendEmail method for sending attachment
//     @Override
//     public void sendEmail(
//         String sendTo, String subject, String body, ByteArrayInputStream excelStream, String filename
//         ) throws RuntimeException {
        
//         // Sending email using MimeMessage along with MimeMessageHelper from spring-boot-starter-mail
//         try {
//             MimeMessage message = javaMailSender.createMimeMessage();

//             // Setting the mutipart message as true for sending attachments
//             MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
//             helper.setFrom(senderEmail);
//             helper.setTo(sendTo);
//             helper.setSubject(subject);
//             helper.setText(body, true); // Second parameter is true as there is HTML in the bady

//             // Type casting ByteArrayInputStream to ByteArrayDataStream and then upcasting it to DataSource,
//             // as DataSource is accepted as a parameter in addAttachment()
//             DataSource dataSource = new ByteArrayDataSource(excelStream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

//             helper.addAttachment(filename, dataSource);

//             javaMailSender.send(message);
//         } catch (Exception e) {
//             logger.error("[MimeMessage, JavaMailSender] failed to send email", e);
//             throw new RuntimeException(e.getMessage());
//         }
        
//     }

// }
