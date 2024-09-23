package com.example.Users_service.exceptions;

@SuppressWarnings("serial")
public class UserAlreadExistsException extends RuntimeException{

	public UserAlreadExistsException(String msg) {
		super(msg);
	}
}
