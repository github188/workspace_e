package com.nari.runman.archivesman;
/**
 * 档案导入长度判断异常
 * @author zhangzhw
 *
 */
public class LengthException extends Exception {
	
	public String message;
	
	
	public LengthException(String message)
	{
		super();
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
