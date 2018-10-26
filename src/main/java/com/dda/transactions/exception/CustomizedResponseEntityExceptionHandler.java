package com.dda.transactions.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.dda.transactions.model.ErrorDetails;
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(value = AccountNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleLoanAccountNotFoundException(AccountNotFoundException ex)
	{

		ErrorDetails exceptionResponse = new ErrorDetails("Account not found", "404");

           return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(value = BadRequestException.class)

    public final ResponseEntity<ErrorDetails> handleBadRequest(BadRequestException ex) {

    	ErrorDetails exceptionResponse = new ErrorDetails("Invalid Account ID", "400");

           return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
    

    @ExceptionHandler(Exception.class)

    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex) {

    	ErrorDetails exceptionResponse = new ErrorDetails("Please contact to Admin", "500");

           return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
    @ExceptionHandler(value = StatusBadRequestException.class)

    public final ResponseEntity<ErrorDetails> handleStatusBadRequest(StatusBadRequestException ex) {

    	ErrorDetails exceptionResponse = new ErrorDetails("Please Check Your Status..(Status Should be PENDING)", "400");

           return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
    
    @Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Object>(new ErrorDetails(ex.getMessage(),"405"), HttpStatus.METHOD_NOT_ALLOWED);
	}


/*
    @ExceptionHandler(value = HeaderNotAcceptableException.class)

    public final ResponseEntity<ExceptionResponse> handleHeaderNotAcceptableException(HeaderNotAcceptableException ex) {

           ExceptionResponse exceptionResponse = new ExceptionResponse("Request Header is not acceptable", "406");

           return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

    }





   */
}
