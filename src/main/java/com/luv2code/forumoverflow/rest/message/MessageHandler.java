package com.luv2code.forumoverflow.rest.message;

/**
 * Created by lzugaj on Saturday, April 2020
 */

public final class MessageHandler {

    public static String entityNotFound(String entityName, Long entityId) {
        return String.format("%s with id `%s` wasn't founded.", entityName, entityId);
    }

    public static String entityNameAlreadyExists(String entityName, String name) {
        return String.format("%s with name `%s` already exists.", entityName, name);
    }

    public static String entityUsernameAlreadyExists(String entityName, String username) {
        return String.format("%s with username `%s` already exists.", entityName, username);
    }

    public static String entityEmailAlreadyExists(String entityName, String email) {
        return String.format("%s with email `%s` already exists.", entityName, email);
    }
}
