package com.example.Users_service.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message){
		super(message);
	}
}
