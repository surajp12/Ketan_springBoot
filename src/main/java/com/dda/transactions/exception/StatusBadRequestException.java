package com.dda.transactions.exception;

public class StatusBadRequestException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public StatusBadRequestException(String exception)
	{
		super(exception);
	}
}
