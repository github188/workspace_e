package com.nari.exception;

public class ExceptionInfoDTO extends BaseDTO {
	
    static final long serialVersionUID = 5853337547949414866L;
    private String messageCode = null;
    private boolean contextSensitive = false;
    private boolean confirmationInd = false;
    private String loggingType = null;
    
    public ExceptionInfoDTO(String messageCode, boolean contextSensitive,
            boolean confirmation, String loggingType) {
        this.messageCode = messageCode;
        this.contextSensitive = contextSensitive;
        this.confirmationInd = confirmation;
        this.loggingType = loggingType;
    }

    public boolean isConfirmationInd() {
        return confirmationInd;
    }

    public boolean isContextSensitive() {
        return contextSensitive;
    }

    public String getLoggingType() {
        return loggingType;
    }

    public String getMessageCode() {
        return messageCode;
    }
}