package com.ecommerce.app.mail.service;

public interface EmailSender {
    void sendEmail(String to, String subject, String content);
}
