package com.manideep.spendguide.service;

public interface EmailService {

    // Creates and sends email using SimpleMailMessage from spring-boot-starter-mail
    void sendEmail(String sendTo, String subject, String body);

}
