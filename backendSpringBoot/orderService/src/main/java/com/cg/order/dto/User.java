package com.cg.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private long userId;

	private String userName;

	private String userEmail;

	private String userPassword;

	private String userPhoneNumber;

	private String userAddress;
}
