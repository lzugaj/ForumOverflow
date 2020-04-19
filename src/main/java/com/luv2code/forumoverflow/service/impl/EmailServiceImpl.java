package com.luv2code.forumoverflow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.service.EmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Friday, April 2020
 */

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(final User user) {
        try {
            final SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("ivanzugaj007@gmail.com");
            mailMessage.setSubject("Gmail iz Spring Boot Java Projekta");
            mailMessage.setText("Pozdrav Pape,\n\nŠaljem ti mail sa svog backenda napisanog u Javi! :D\n\nLijep pozdrav,\nLuka Žugaj");
            mailMessage.setFrom("luka.zugaj7@gmail.com");
            mailMessage.setSentDate(new Date());

            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            log.info(e.getMessage());
        }
    }
}
