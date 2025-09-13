package com.manideep.spendguide.service;

import java.io.ByteArrayInputStream;

public interface EmailService {

    // Creates and sends email using SimpleMailMessage from spring-boot-starter-mail
    void sendEmail(String sendTo, String subject, String body) throws RuntimeException;

    // Overload sendEmail method for sending attachment
    void sendEmail(String sendTo, String subject, String body, ByteArrayInputStream excelStream, String filename) throws RuntimeException;

}
