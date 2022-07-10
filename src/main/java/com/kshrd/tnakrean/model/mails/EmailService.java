package com.kshrd.tnakrean.model.mails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(SimpleEmail details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
