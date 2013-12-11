package com.nari.exception;

public class AppException extends BaseException {
	
	private static final long serialVersionUID = -840615912262073681L;

	public AppException(String errorCode) {
		super((String) null, new String[] {errorCode, null });
	}

	public AppException(String msg, String errorCode) {
		super(msg, new String[] {errorCode, null });
	}

	public AppException(String msg, String errorCode, String errorMsg) {
		super(msg, new String[] {errorCode, errorMsg });
	}

	public AppException(Exception e, String errorCode) {
		super(e, new String[] {errorCode, null });
	}

	public AppException(Exception e, String errorCode, String errorMsg) {
		super(e, new String[] {errorCode, errorMsg });
	}
}