package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Sunday, April 2020
 */

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
