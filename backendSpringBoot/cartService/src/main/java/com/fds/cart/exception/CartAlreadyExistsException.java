package com.fds.cart.exception;

@SuppressWarnings("serial")
public class CartAlreadyExistsException extends RuntimeException {

	public CartAlreadyExistsException(String msg) {
		super(msg);
	}
}
