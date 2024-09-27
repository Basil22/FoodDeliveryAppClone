package com.fds.users.dto;

public class LoginRequestDto {

	private String userPhoneNumber;
	private String userPassword;

	public LoginRequestDto() {

	}

	public LoginRequestDto(String userPhoneNumber, String userPassword) {
		super();
		this.userPhoneNumber = userPhoneNumber;
		this.userPassword = userPassword;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
