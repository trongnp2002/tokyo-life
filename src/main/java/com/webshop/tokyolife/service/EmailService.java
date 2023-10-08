package com.webshop.tokyolife.service;

public interface EmailService {
    public void sendMail(String to, String subject, String body) throws Exception;
}
