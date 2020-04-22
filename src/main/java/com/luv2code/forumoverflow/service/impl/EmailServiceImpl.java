package com.luv2code.forumoverflow.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.notification.ActiveUserStatusNotification;
import com.luv2code.forumoverflow.domain.notification.BlockUserStatusNotification;
import com.luv2code.forumoverflow.domain.notification.InactiveUserStatusNotification;
import com.luv2code.forumoverflow.domain.notification.Notification;
import com.luv2code.forumoverflow.service.EmailService;
import com.luv2code.forumoverflow.util.Constants;
import com.luv2code.forumoverflow.util.message.EmailBlockerMessage;
import com.luv2code.forumoverflow.util.message.EmailInactiveMessage;

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
    public void sendBlockerNotification(final User user) {
        final SimpleMailMessage blockerMessage = new SimpleMailMessage();
        blockerMessage.setTo(user.getEmail());
        blockerMessage.setSubject(EmailBlockerMessage.BLOCKER_SUBJECT);
        blockerMessage.setText(EmailBlockerMessage.getBlockerMessage(user));
        blockerMessage.setSentDate(new Date());
        blockerMessage.setFrom(Constants.ADMIN_EMAIL);
        javaMailSender.send(blockerMessage);
    }

    @Override
    public void sendInactiveNotification(final User user) {
        final SimpleMailMessage inactiveMessage = new SimpleMailMessage();
        inactiveMessage.setTo(user.getEmail());
        inactiveMessage.setSubject(EmailInactiveMessage.INACTIVE_SUBJECT);
        inactiveMessage.setText(EmailInactiveMessage.getInactiveMessage(user));
        inactiveMessage.setSentDate(new Date());
        inactiveMessage.setFrom(Constants.ADMIN_EMAIL);
        javaMailSender.send(inactiveMessage);
    }

    @Override
    public void sendUserStatusChangedNotification(final User updatedUser) {
        Notification notification;
        if (updatedUser.getUserStatus().getName().equals(Constants.ACTIVE)) {
            notification = new ActiveUserStatusNotification(updatedUser, javaMailSender);
            notification.send();
        } else if (updatedUser.getUserStatus().getName().equals(Constants.INACTIVE)) {
            notification = new InactiveUserStatusNotification(updatedUser, javaMailSender);
            notification.send();
        } else {
            notification = new BlockUserStatusNotification(updatedUser, javaMailSender);
            notification.send();
        }
    }
}
