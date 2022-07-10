package com.kshrd.tnakrean.model.mails;

import org.springframework.stereotype.Service;

@Service
public interface Emails {
    // Method
    // To send a simple email
    String sendSimpleMail(SimpleEmail details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
