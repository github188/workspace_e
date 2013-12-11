package com.nari.action.baseaction;

import org.apache.struts2.json.annotations.JSON;

import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 类ExceptionAction
 *
 * @author zhangzhw
 * @describe 全局异常处理，返回异常信息到前台
 */
public class ExceptionAction extends BaseAction {

	public boolean success;
	public Errors errors;
	public Exception exception;
	public String message;

	public String execute() {
		this.setSuccess(false);
		this.errors = new Errors();
		Exception e=this.getException();
		this.processException(e);
		e.printStackTrace();


		return "success";
	}

	// 分析异常并返回到前台结果
	private void processException(Exception e) {
		if (e instanceof DBAccessException)
			this.errors.setMsg(((DBAccessException) e).getMessage());
		else if (e instanceof ServiceException)
			this.errors.setMsg(((ServiceException) e).getMessage());
		else
			this.errors.setMsg("未知错误，可能是ACTION中处理错误");
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return this.errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	@JSON(serialize = false)
	public Exception getException() {
		return this.exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
