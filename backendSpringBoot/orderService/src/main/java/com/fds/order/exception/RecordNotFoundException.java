package com.fds.order.exception;

@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException {
	public RecordNotFoundException(String message) {
		super(message);
	}

}
