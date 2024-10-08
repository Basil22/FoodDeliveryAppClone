package com.fds.payments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", request.getDescription(false));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}