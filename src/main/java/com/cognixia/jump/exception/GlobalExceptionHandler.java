package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

// advise controller on what to do when a certain exception is thrown
@ControllerAdvice
public class GlobalExceptionHandler {

	// ex 	   -> represents the exception object that is thrown by the program
	// request -> a copy of the request sent to spring
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValid( MethodArgumentNotValidException ex, WebRequest request ) {
			
		// The following will find all the error messages that were found when validating the fields in the request and formatting our message so it can be passed
		// within our response
		StringBuilder errors = new StringBuilder("");
		for(FieldError fe : ex.getBindingResult().getFieldErrors()) {
			errors.append( "[" + fe.getField() + " : " + fe.getDefaultMessage() + "]; " );
		}
			
		// request.getDescription() -> details on the request (usually just includes the uri/url )
		ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(), request.getDescription(false));
			
		// give a general 400 status code to indicate error on client end
		return ResponseEntity.status(400).body(errorDetails);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(404).body(errorDetails);
	}
	
	@ExceptionHandler(TeamOverflowException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<?> teamOverflow(TeamOverflowException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(403).body(errorDetails);
	}
	
	@ExceptionHandler(TeamUnderflowException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<?> teamUnderflow(TeamUnderflowException ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(403).body(errorDetails);
	}
}