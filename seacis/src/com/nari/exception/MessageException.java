package com.nari.exception;

public class MessageException extends BaseException {
	
	private static final long serialVersionUID = 4203327951964803701L;

	public MessageException(String errorCode) {
		super((String) null, new String[] { errorCode, null });
	}

	public MessageException(String msg, final String errorCode) {
		super(msg, new String[] { errorCode, null });
	}

	public MessageException(Exception e, String errorCode) {
		super(e, new String[] { errorCode, null });
	}

	public MessageException(Exception e, String errorCode, String errorMsg) {
		super(e, new String[] { errorCode, errorMsg });
	}
}