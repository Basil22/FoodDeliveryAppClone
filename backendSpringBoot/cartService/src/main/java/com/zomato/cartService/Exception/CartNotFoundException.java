package com.zomato.cartService.Exception;

@SuppressWarnings("serial")
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
