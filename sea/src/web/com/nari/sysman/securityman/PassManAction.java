package com.nari.sysman.securityman;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PSysUser;
import com.nari.util.PasswordUtils;
import com.opensymphony.xwork2.ActionContext;

public class PassManAction extends BaseAction {
	private static Logger logger = Logger.getLogger(RoleManAction.class);

	IAuthorizeManager iAuthorizeManager;

	public boolean success = true;
	// 浏览器端请求的参数
	private String pass1;
	private String oldPass;
	public Errors errors;

	public String updatePass() throws Exception {

		String password = PasswordUtils.md5(pass1);

		PSysUser pSysUser = super.getPSysUser();
		if (!pSysUser.getPwd().equals(PasswordUtils.md5(oldPass))) {
			this.setSuccess(false);
			errors = new Errors();
			errors.setMsg("旧密码不匹配！");

		} else {
			pSysUser.setPwd(password);
			this.iAuthorizeManager.updatePSysUser(pSysUser);
		}

		return SUCCESS;
	}

	@JSON(serialize = false)
	public IAuthorizeManager getiAuthorizeManager() {
		return iAuthorizeManager;
	}

	public void setiAuthorizeManager(IAuthorizeManager iAuthorizeManager) {
		this.iAuthorizeManager = iAuthorizeManager;
	}

	public String getPass1() {
		return pass1;
	}

	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}
	
	

}
