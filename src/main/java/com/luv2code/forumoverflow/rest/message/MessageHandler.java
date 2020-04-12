package com.luv2code.forumoverflow.rest.message;

/**
 * Created by lzugaj on Saturday, April 2020
 */

public final class MessageHandler {

    public static String entityNotFound(String entityName, Long entityId) {
        return entityName + " with id `" + entityId + "` wasn't founded.";
    }

    public static String entityNameAlreadyExists(String entityName, String name) {
        return entityName + " with name `" + name + "` already exists.";
    }
}
