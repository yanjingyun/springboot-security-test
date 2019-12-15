package com.yjy.system.exception;

public class UserNameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -2209871820173080935L;

	public UserNameAlreadyExistException() {
	}

	public UserNameAlreadyExistException(String message) {
		super(message);
	}
}
