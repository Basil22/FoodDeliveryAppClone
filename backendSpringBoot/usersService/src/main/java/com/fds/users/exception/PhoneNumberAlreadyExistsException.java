package com.fds.users.exception;

@SuppressWarnings("serial")
public class PhoneNumberAlreadyExistsException extends RuntimeException {

	public PhoneNumberAlreadyExistsException(String message) {

		super(message);

	}
}