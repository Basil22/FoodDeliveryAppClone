package com.fds.users.exceptions;

@SuppressWarnings("serial")
public class UserAlreadExistsException extends RuntimeException{

	public UserAlreadExistsException(String msg) {
		super(msg);
	}
}
