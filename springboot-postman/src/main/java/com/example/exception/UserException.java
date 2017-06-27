package com.example.exception;

public class UserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1157498606072675042L;

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

}
