package com.kodakro.jiki.controller.exception;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;

/**
 * HandlerExceptionResolver
 * Any Spring bean declared in the DispatcherServlet's application context 
 * that implements HandlerExceptionResolver will be used to intercept 
 * and process any exception raised in the MVC system and not handled by a Controller. 
 * The interface looks like this:
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity handleResourceNotFoundException(final ResourceNotFoundException ex) {
		final String message = ex.getResourceName() 
									   + "."+ ex.getFieldName()+ "=" + ex.getFieldValue() + " not found.";
		return new ResponseEntity<CustomResponseType<Backlog>>(
				new CustomResponseType<Backlog>("KO", null, message), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public ResponseEntity handleResourceNotFoundException(final IncorrectResultSizeDataAccessException ex) {
		return new ResponseEntity<CustomResponseType<Backlog>>(
				new CustomResponseType<Backlog>("KO", null, " Incorrect Result."), HttpStatus.OK);
	}
}
