package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.EmailMessage;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.service.serviceInter.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    @Autowired
    final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ApiResponse<EmailMessage> sendEmail(@RequestParam String subject, @RequestParam String message, @RequestParam String email) {
        try {
            emailService.send(subject, message, email);
        } catch (MessagingException e) {
           return ApiResponse.<EmailMessage>setError("failed to send email");
        }
        EmailMessage emailMessage = new EmailMessage(subject, message, email);
        return ApiResponse.<EmailMessage>ok(EmailMessage.class.getSimpleName())
                .setResponseMsg("Send Email Successfully").setData(emailMessage);
    }
}
