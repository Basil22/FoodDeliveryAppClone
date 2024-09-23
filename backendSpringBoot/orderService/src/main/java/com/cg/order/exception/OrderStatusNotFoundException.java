package com.cg.order.exception;

@SuppressWarnings("serial")
public class OrderStatusNotFoundException extends RuntimeException {
	public OrderStatusNotFoundException(String message) {
		super(message);
	}

}
