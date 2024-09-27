package com.fds.cart.exception;

@SuppressWarnings("serial")
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
