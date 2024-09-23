package com.fds.vendor.exception;

@SuppressWarnings("serial")
public class ItemExistsException extends RuntimeException{

	public ItemExistsException(String msg) {
		super(msg);
	}
}
