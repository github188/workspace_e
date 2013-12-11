package com.nari.exception;

public class WSIFArchiveOperateException extends Exception{
	
	private static final long serialVersionUID = 5319811793995569963L;
	private int errorCode ;

	public WSIFArchiveOperateException(){
		super() ;
	}
	
	public WSIFArchiveOperateException(String message){
		super(message) ;
	}
	
	public WSIFArchiveOperateException(int errorCode,String message){
		super(message) ;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}