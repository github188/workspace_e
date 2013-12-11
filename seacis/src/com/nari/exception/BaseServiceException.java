package com.nari.exception;

public class BaseServiceException extends RuntimeException {

	private static final long serialVersionUID = 4975973883027651032L;

	public BaseServiceException(String message) {
		super(message);
	}
}