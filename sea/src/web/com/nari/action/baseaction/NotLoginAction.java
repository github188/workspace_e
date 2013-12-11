package com.nari.action.baseaction;

/**
 * Action 类NotLoginAction
 * 
 * @author zhangzhw
 * @describe 未登录时的返回
 */
public class NotLoginAction {
	public boolean notlogin = true;
	public boolean success=true;

	public String notlogin() {
		return "success";
	}

	public boolean isNotlogin() {
		return notlogin;
	}

	public void setNotlogin(boolean notlogin) {
		this.notlogin = notlogin;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	

}
