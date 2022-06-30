package com.kshrd.tnakrean.service.serviceInter;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@NoArgsConstructor
public class EmailService {
    private final static String EMAIL_CONFIRMATION_SUBJECT = "Confirm your tnakrean account";

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String token, String email) throws MessagingException {
        // build email
        // send message
        String message = "Welcome to , test token" + token;
        String from = "no-reply@.org";
        send(from, message, email);
    }

    @Async
    public void send(String subject, String message, String to) throws MessagingException {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);
            javaMailSender.send(mimeMessage);

    }
}