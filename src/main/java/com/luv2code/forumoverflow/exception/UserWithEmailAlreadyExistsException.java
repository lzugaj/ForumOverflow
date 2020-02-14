package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Friday, February 2020
 */

public class UserWithEmailAlreadyExistsException extends AbstractEntityException {

	private static final long serialVersionUID = 1L;

	public UserWithEmailAlreadyExistsException(String entityName, String fieldName, String fieldValue) {
		super(entityName, fieldName, fieldValue, null);
	}

	public UserWithEmailAlreadyExistsException(String entityName, String fieldName, String fieldValue, Throwable cause) {
		super(entityName, fieldName, fieldValue, createMessage(entityName, fieldName, fieldValue), cause);
	}

	private static String createMessage(String entityName, String fieldName, String fieldValue) {
		return String.format("Entity '%s' with '%s' value '%s' already exists.",
				entityName, fieldName, fieldValue);
	}
}
