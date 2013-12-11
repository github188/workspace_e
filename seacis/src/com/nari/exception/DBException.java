package com.nari.exception;

public class DBException extends BaseException {

	private static final long serialVersionUID = 1L;

	public DBException(String errorCode) {
		super((String) null, new String[] { errorCode, null });
	}

	public DBException(String msg, String errorCode) {
		super(msg, new String[] { errorCode, null });
	}

	public DBException(Exception e, String errorCode) {
		super(e, new String[] { errorCode, null });
	}

	public DBException(Exception e, String errorCode, String errorMsg) {
		super(e, new String[] { errorCode, errorMsg });
	}
}