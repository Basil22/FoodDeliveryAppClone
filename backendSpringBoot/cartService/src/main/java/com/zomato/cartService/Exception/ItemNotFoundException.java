package com.zomato.cartService.Exception;

@SuppressWarnings("serial")
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
