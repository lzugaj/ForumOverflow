package com.luv2code.forumoverflow.util.message;

import com.luv2code.forumoverflow.domain.User;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class EmailBlockerMessage {

    public static final String BLOCKER_SUBJECT = "Your status has been set to BLOCKED";

    public static String getBlockerMessage(final User user) {
        return String.format("Dear %s %s,\n\n Your status has been changed to blocked because you were reported 3 times for inappropriate content. "
                        + "Currently, your profile is blocked and you are not able to add any new post or comment in ForumOverflow application until Administrator changed your status."
                        + "You can contact Administrator through this email address and try to solve your situation.\n\nKind greetings,\nForumOverflow",
                user.getFirstName(), user.getLastName());
    }
}
