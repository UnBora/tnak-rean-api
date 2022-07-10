package com.kshrd.tnakrean.model.mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email/")
public class EmailControllers {

    @Autowired
    private Emails emails;
    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody SimpleEmail details)
    {
        String status = emails.sendSimpleMail(details);
        return status;
    }
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details)
    {
        String status = emails.sendMailWithAttachment(details);
        return status;
    }
}
