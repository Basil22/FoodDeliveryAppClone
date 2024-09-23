package com.fds.vendor.exception;

@SuppressWarnings("serial")
public class VendorAlreadyExistsException extends RuntimeException{

	public VendorAlreadyExistsException(String msg) {
		super(msg);
	}
}
