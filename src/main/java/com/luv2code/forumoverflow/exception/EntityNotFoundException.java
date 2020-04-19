package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Friday, February 2020
 */

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
