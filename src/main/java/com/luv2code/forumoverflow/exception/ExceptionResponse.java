package com.luv2code.forumoverflow.exception;

import java.time.LocalDateTime;

/**
 * Created by lzugaj on Saturday, April 2020
 */

public class ExceptionResponse {

    private final String message;

    private final String details;

    private final LocalDateTime timestamp;

    public ExceptionResponse(final String message, final String details, final LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
