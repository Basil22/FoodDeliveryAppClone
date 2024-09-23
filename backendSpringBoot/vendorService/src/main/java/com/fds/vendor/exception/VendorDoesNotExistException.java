package com.fds.vendor.exception;

@SuppressWarnings("serial")
public class VendorDoesNotExistException extends RuntimeException{

	public VendorDoesNotExistException(String msg) {
		super(msg);
	}
}
