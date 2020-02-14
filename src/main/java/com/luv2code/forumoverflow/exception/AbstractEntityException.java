package com.luv2code.forumoverflow.exception;

/**
 * Created by lzugaj on Friday, February 2020
 */

public class AbstractEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String entityName;

	private String fieldName;

	private String fieldValue;

	public AbstractEntityException(String entityName, String fieldName, String fieldValue, String message) {
		this(entityName, fieldName, fieldValue, message, null);
	}

	public AbstractEntityException(String entityName, String fieldName, String fieldValue, String message, Throwable cause) {
		super(message, cause);
		this.entityName = entityName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
}
