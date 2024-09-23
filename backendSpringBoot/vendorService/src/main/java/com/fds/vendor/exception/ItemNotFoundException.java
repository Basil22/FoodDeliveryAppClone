package com.fds.vendor.exception;

@SuppressWarnings("serial")
public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(String msg) {
		super(msg);
	}
}
