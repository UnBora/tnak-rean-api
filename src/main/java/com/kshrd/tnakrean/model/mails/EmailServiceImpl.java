package com.kshrd.tnakrean.model.mails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.MalformedURLException;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.sender.user}")
    private String sender;

    public String sendSimpleMail(SimpleEmail details)
    {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getReceiver());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail hase been sent to your user successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error while Sending Mail";
        }
    }
    // send your email with the file attachement
    public String
    sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            // Set up your content to send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getReceiver());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            // Adding the attachment
            Resource resource = new UrlResource(details.getAttachment());
            mimeMessageHelper.addAttachment(resource.getFilename(), resource);
            System.out.println("attachment:"+resource);
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail hase been sent to your user successfully with the files that you have been attached...";
        }
        catch (MessagingException e) {
            // Display message when exception occurred
            return "Error while sending mail!!!";
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}