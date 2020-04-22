package com.luv2code.forumoverflow.util.message;

import com.luv2code.forumoverflow.domain.User;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class EmailInactiveMessage {

    public static final String INACTIVE_SUBJECT = "Your status has been set to INACTIVE";

    public static String getInactiveMessage(User user) {
        return String.format("Dear %s %s,\n\n Your status has been changed to inactive because you were reported for inappropriate content. "
                        + "Currently, your profile is inactive for next 24 hours and in that period of time you won't be able to add any new post or comment. "
                        + "But, you can login to application and read new posts and comments from other users.\n"
                        + "Also you have been reported %d times for inappropriate content. If you will reached 3 reports your profile will be blocked and "
                        + "unavailable for a longer period of time. We hope that this was just an exception and mistake and it will not happen again.\n\nKind greetings,\nForumOverflow",
                user.getFirstName(), user.getLastName(), user.getBlockerCounter());
    }
}
