package com.luv2code.forumoverflow.domain.notification;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class ActiveUserStatusNotification implements Notification {

    public static final String SUBJECT = "Your status has been changed to ACTIVE";

    private final User user;

    private final JavaMailSender javaMailSender;

    public ActiveUserStatusNotification(final User user, final JavaMailSender javaMailSender) {
        this.user = user;
        this.javaMailSender = javaMailSender;
    }

    private String getSubject() {
        return SUBJECT;
    }

    private String getMessage(final User user) {
        return String.format("Dear %s %s,\n\n"
                + "Your profile has been changed and it is now active again. Enjoy in usage of ForumOverflow application.\n\n"
                + "King greetings,\n"
                + "ForumOverflow", user.getFirstName(), user.getLastName());
    }

    @Override
    public void send() {
        final SimpleMailMessage activeStatusMessage = new SimpleMailMessage();
        activeStatusMessage.setTo(user.getEmail());
        activeStatusMessage.setSubject(getSubject());
        activeStatusMessage.setText(getMessage(user));
        activeStatusMessage.setSentDate(new Date());
        activeStatusMessage.setFrom(Constants.ADMIN_EMAIL);
        javaMailSender.send(activeStatusMessage);
    }
}
