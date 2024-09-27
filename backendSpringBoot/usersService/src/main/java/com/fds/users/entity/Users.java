package com.fds.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@NotBlank(message = "Username cannot be blank")
	@Size(min = 5, max = 10, message = "Username must be between 5 and 10 characters")
	private String userName;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email Should be valid")
	private String userEmail;

	@NotBlank(message = "Password cannot be blank")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain letters, symbols, and numbers")
	private String userPassword;

	@NotBlank(message = "Phone number cannot be blank")

	@Pattern(regexp = "^[6-9][0-9]{9}$",

			message = "Phone number must start with 6, 7, 8, or 9 and be 10 digits long")

	private String userPhoneNumber;

	@NotBlank(message = "Address cannot be blank")

	@Size(min = 10, message = "Address must be at least 10 characters long")

	private String userAddress;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Users(long userId,
			@NotBlank(message = "Username cannot be blank") @Size(min = 5, max = 10, message = "Username must be between 5 and 10 characters") String userName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email Should be valid") String userEmail,
			@NotBlank(message = "Password cannot be blank") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain letters, symbols, and numbers") String userPassword,
			@NotBlank(message = "Phone number cannot be blank") @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must start with 6, 7, 8, or 9 and be 10 digits long") String userPhoneNumber,
			@NotBlank(message = "Address cannot be blank") @Size(min = 10, message = "Address must be at least 10 characters long") String userAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userPhoneNumber = userPhoneNumber;
		this.userAddress = userAddress;
	}

	public Users() {

	}

}