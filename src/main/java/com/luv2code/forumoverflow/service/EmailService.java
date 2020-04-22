package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;

/**
 * Created by lzugaj on Friday, April 2020
 */

public interface EmailService {

    void sendBlockerNotification(final User user);

    void sendInactiveNotification(final User user);

    void sendUserStatusChangedNotification(final User updatedUser);

}
