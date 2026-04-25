package com.gaurav.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendVerificationOtpEmail(String userEmail, String otp) throws MessagingException, MailSendException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//
//
//        String subject = "Account verification";
//        String text = "your account verification code is : " + otp;
//
//        helper.setSubject(subject);
//        helper.setText(text, true);
//        helper.setTo(userEmail);
//
//        try {
//            javaMailSender.send(mimeMessage);
//        } catch (MailException e) {
//            throw new MailSendException("Failed to send email");
//        }
//    }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom("Cryptex <cryptexnoreply@gmail.com>");
            helper.setTo(userEmail);
            helper.setSubject("Cryptex Account Verification");

            String body =
                    "Your Cryptex verification code is: " + otp +
                            "\n\nThis code is valid for 5 minutes.";

            helper.setText(body, false); // ❗ plain text

            javaMailSender.send(message);

        } catch (MailException | MessagingException e) {
            // log real cause
            throw new RuntimeException("Email send failed", e);
        }
    }
}
