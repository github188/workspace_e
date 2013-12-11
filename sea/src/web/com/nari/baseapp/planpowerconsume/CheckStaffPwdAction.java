package com.nari.baseapp.planpowerconsume;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.util.PasswordUtils;
import com.opensymphony.xwork2.ActionContext;

public class CheckStaffPwdAction extends BaseAction{
	private boolean checkStaffRes;
	private boolean success = true;
	private String staffPwd;
	
	public String checkStaffPwd() {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			success = false;
			return SUCCESS;
		}
		checkStaffRes = false;//默认校验失败
		if(staffPwd ==null || staffPwd=="") {//当密码为空时 直接返回校验失败
			return SUCCESS;
		}
		
		if(user.getPwd().equals(PasswordUtils.md5(staffPwd))){
			//只有当密码等于操作员密码时 校验才能成功
			checkStaffRes = true;
		}
		return SUCCESS;
	}
	
	public boolean isCheckStaffRes() {
		return checkStaffRes;
	}
	public void setCheckStaffRes(boolean checkStaffRes) {
		this.checkStaffRes = checkStaffRes;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStaffPwd() {
		return staffPwd;
	}

	public void setStaffPwd(String staffPwd) {
		this.staffPwd = staffPwd;
	}
}
