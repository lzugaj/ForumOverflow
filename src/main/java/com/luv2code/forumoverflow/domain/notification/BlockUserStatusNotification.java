package com.luv2code.forumoverflow.domain.notification;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class BlockUserStatusNotification implements Notification {

    public static final String SUBJECT = "Your status has been changed to INACTIVE";

    private final User user;

    private final JavaMailSender javaMailSender;

    public BlockUserStatusNotification(User user, JavaMailSender javaMailSender) {
        this.user = user;
        this.javaMailSender = javaMailSender;
    }

    private String getSubject() {
        return SUBJECT;
    }

    private String getMessage(final User user) {
        return String.format("Dear %s %s,\n\n"
                + "Your profile has been changed and it is now blocked. It will remain like this unit Administrator manually changed your status."
                + "You can contact Administrator through this email address for more information.\n\n"
                + "King greetings,\n"
                + "ForumOverflow", user.getFirstName(), user.getLastName());
    }

    @Override
    public void send() {
        final SimpleMailMessage blockStatusMessage = new SimpleMailMessage();
        blockStatusMessage.setTo(user.getEmail());
        blockStatusMessage.setSubject(getSubject());
        blockStatusMessage.setText(getMessage(user));
        blockStatusMessage.setSentDate(new Date());
        blockStatusMessage.setFrom(Constants.ADMIN_EMAIL);
        javaMailSender.send(blockStatusMessage);
    }
}
