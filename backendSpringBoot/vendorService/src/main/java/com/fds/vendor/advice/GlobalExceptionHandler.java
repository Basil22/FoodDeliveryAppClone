package com.fds.vendor.advice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fds.vendor.exception.InvalidFssaiException;
import com.fds.vendor.exception.ItemExistsException;
import com.fds.vendor.exception.ItemNotFoundException;
import com.fds.vendor.exception.VendorAlreadyExistsException;
import com.fds.vendor.exception.VendorDoesNotExistException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ VendorDoesNotExistException.class })
	public ResponseEntity<Object> handleVendorDoesNotExistException(VendorDoesNotExistException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({ VendorAlreadyExistsException.class })
	public ResponseEntity<Object> handleVendorAlreadyExistsException(VendorAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ ItemNotFoundException.class })
	public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler({ ItemExistsException.class })
	public ResponseEntity<Object> handleItemExistsException(ItemExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ InvalidFssaiException.class })
	public ResponseEntity<Object> handleInvalidFssaiException(InvalidFssaiException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		Map<String, String> errorBody = exception.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (first, last) -> first));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(violation -> {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}