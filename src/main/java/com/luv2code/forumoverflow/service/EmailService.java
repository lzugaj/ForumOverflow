package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;

/**
 * Created by lzugaj on Friday, April 2020
 */

public interface EmailService {

    void send(final User user);

}
