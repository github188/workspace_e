package com.nari.exception;

public class ExceptionDTO extends BaseDTO {

	static final long serialVersionUID = 8010208372246034996L;
	String messageCode;
	boolean confirmation = false;
	String loggingType;

	public ExceptionDTO() {
	}

	public ExceptionDTO(String messageCode, boolean confirmationInd,String loggingType) {
		this.messageCode = messageCode;
		this.confirmation = confirmationInd;
		this.loggingType = loggingType;
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getLoggingType() {
		return loggingType;
	}

	public void setLoggingType(String loggingType) {
		this.loggingType = loggingType;
	}
}