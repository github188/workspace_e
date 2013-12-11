package com.nari.sysman.securityman;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 拦截器类 CheckLogin
 * 
 * @author zhangzhw
 * @describe 拦截未登录用户，并返回未登录标志
 */
public class CheckLogin implements Interceptor {

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invoke) throws Exception {
		Object user = getSession().getAttribute("pSysUser");

		if (user == null) {
			return "notlogin";
		}

		return invoke.invoke();
	}

}
