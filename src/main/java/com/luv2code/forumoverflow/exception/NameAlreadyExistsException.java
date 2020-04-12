package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Saturday, April 2020
 */

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
