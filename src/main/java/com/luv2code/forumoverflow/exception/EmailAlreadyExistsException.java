package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Sunday, April 2020
 */

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
