package com.nari.exception;

public class CommunicationException extends BaseException {

	private static final long serialVersionUID = 1L;

	public CommunicationException(String errorCode) {
		super((String) null, new String[] {errorCode, null });
	}

	public CommunicationException(String msg, String errorCode) {
		super(msg, new String[] { errorCode, null });
	}

	public CommunicationException(Exception e, String errorCode) {
		super(e, new String[] { errorCode, null });
	}

	public CommunicationException(Exception e, String errorCode, String errorMsg) {
		super(e, new String[] { errorCode, errorMsg });
	}
}