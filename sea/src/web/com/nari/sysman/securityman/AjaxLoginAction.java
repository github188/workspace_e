package com.nari.sysman.securityman;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PSysUser;
import com.nari.util.PasswordUtils;
import com.nari.util.ws.PropertiesUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * Action类 AjaxLoginAction
 * 
 * @author zhangzhw
 * @describe 使用Ajax登录的处理
 */
public class AjaxLoginAction extends BaseAction {

	protected IPSysUserManager iPSysUserManager;
	private String staffNo;
	private String password;
	private PSysUser pSysUser;
	public boolean success = true;
	public Errors errors;

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		Map session = ActionContext.getContext().getSession();
		password = PasswordUtils.md5(password);

		pSysUser = iPSysUserManager.login(staffNo, password);

		if (pSysUser == null) {
			setSuccess(false);
			errors = new Errors();
			errors.setMsg("用户名不存在！");
		} else if (!pSysUser.getPwd().equals(password)) {
			setSuccess(false);
			errors = new Errors();
			errors.setMsg("密码不正确！");

		} else if (!pSysUser.getCurStatusCode().equals("01")) {
			setSuccess(false);
			errors = new Errors();
			errors.setMsg("用户已锁定或删除！");
		} else {
			ServletContext application = super.getServletContext();
			Map onLineUsers = (Map) application.getAttribute("onLineUsers");
			if (onLineUsers == null) {
				onLineUsers = new HashMap();
				session.put("pSysUser", pSysUser);
				onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
				application.setAttribute("onLineUsers", onLineUsers);
				return "success";
			} else {
				if ("1".equals(PropertiesUtils.getProperty("loginFlag"))) {
					Object userLogin = onLineUsers.get(pSysUser.getStaffNo());
					if (userLogin == null) {
						onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
						application.setAttribute("onLineUsers", onLineUsers);
						session.put("pSysUser", pSysUser);
						super.addUserLog("用户登录,用户工号:" + pSysUser.getStaffNo(), "login",
						"05");
						return "success";
					} else {
						errors.setMsg("该用户已登陆");
						return "failure";
					}						
				} else {
					onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
					application.setAttribute("onLineUsers", onLineUsers);
					session.put("pSysUser", pSysUser);
					super.addUserLog("用户登录,用户工号:" + pSysUser.getStaffNo(), "login",
					"05");
					return "success";
				}
			}
		}

		return "success";
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PSysUser getpSysUser() {
		return pSysUser;
	}

	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public void setiPSysUserManager(IPSysUserManager iPSysUserManager) {
		this.iPSysUserManager = iPSysUserManager;
	}
}
