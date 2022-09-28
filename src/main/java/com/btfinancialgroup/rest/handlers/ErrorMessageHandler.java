package com.btfinancialgroup.rest.handlers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.btfinancialgroup.provider.dto.ErrorMessage;

import feign.FeignException;

@ControllerAdvice
public class ErrorMessageHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintExceptions(ConstraintViolationException e, WebRequest webRequest) {
    	ErrorMessage error = new ErrorMessage();
    	error.setCode(HttpStatus.BAD_GATEWAY.value());
    	error.setMessage("Problem occurred");
    	return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignStatusException(FeignException e, HttpServletResponse httpServletResponse) {
    	ErrorMessage error = new ErrorMessage();
    	String message = null;
    	switch (HttpStatus.valueOf(httpServletResponse.getStatus())) {
	        case NOT_FOUND:
	            message = "Not Found";
	            break;
	        case FORBIDDEN:
	        	message = "Forbidden";
	        	break;
	        default:
	        	message = "Something went wrong";
	        	break;
    	}
    	error.setCode(httpServletResponse.getStatus());
    	error.setMessage(message);
    	return ResponseEntity.status(HttpStatus.valueOf(httpServletResponse.getStatus())).body(error);
    }
}
