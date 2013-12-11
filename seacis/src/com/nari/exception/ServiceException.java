package com.nari.exception;

public class ServiceException extends BaseException {
	
	private static final long serialVersionUID = 2971887272598302055L;

	public ServiceException() {
		super();
	}

	public ServiceException(Exception e, String msg) {
		super(e, msg);
	}

	public ServiceException(String msg) {
		super(msg);
	}
}