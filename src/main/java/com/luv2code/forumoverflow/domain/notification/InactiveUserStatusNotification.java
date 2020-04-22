package com.luv2code.forumoverflow.domain.notification;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class InactiveUserStatusNotification implements Notification {

    public static final String SUBJECT = "Your status has been changed to INACTIVE";

    private final User user;

    private final JavaMailSender javaMailSender;

    public InactiveUserStatusNotification(User user, JavaMailSender javaMailSender) {
        this.user = user;
        this.javaMailSender = javaMailSender;
    }

    private String getSubject() {
        return SUBJECT;
    }

    private String getMessage(final User user) {
        return String.format("Dear %s %s,\n\n"
                + "Your profile has been changed and it is now inactive. It will be active again in next 24 hours\n\n"
                + "King greetings,\n"
                + "ForumOverflow", user.getFirstName(), user.getLastName());
    }

    @Override
    public void send() {
        final SimpleMailMessage inactiveStatusMessage = new SimpleMailMessage();
        inactiveStatusMessage.setTo(user.getEmail());
        inactiveStatusMessage.setSubject(getSubject());
        inactiveStatusMessage.setText(getMessage(user));
        inactiveStatusMessage.setSentDate(new Date());
        inactiveStatusMessage.setFrom(Constants.ADMIN_EMAIL);
        javaMailSender.send(inactiveStatusMessage);
    }
}
