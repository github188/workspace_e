package com.nari.sysman.securityman;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import sun.misc.BASE64Decoder;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.util.PasswordUtils;
import com.nari.util.RealIP;
import com.nari.util.ws.PropertiesUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author 陈建国
 * 
 * @创建时间:2009-12-11 上午11:08:43
 * 
 * @类描述: 系统登录
 * 
 */
public class LoginAction extends BaseAction implements HttpSessionListener {

	protected IPSysUserManager iPSysUserManager;
	private String staffNo;
	private String password;
	private PSysUser pSysUser;
	// 错误信息
	private String errorMsg;

	/**
	 * 系统登录
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String login() {
		Map session = ActionContext.getContext().getSession();
		BASE64Decoder b64 = new sun.misc.BASE64Decoder();
		try {
			staffNo = new String(b64.decodeBuffer(staffNo));
			password = new String(b64.decodeBuffer(password));
		} catch (IOException e) {
			e.printStackTrace();
		}
		password = PasswordUtils.md5(password);
		String ipAddr = RealIP.getIPAddr(this.getRequest());
		try {
			pSysUser = iPSysUserManager.login(staffNo, password);
			if (null == pSysUser) {
				// errorMsg = "用户名不存在！";
				errorMsg = "登录失败，请重新登录！";
				return "failure";
			} else if (null == pSysUser.getPwd()
					|| !pSysUser.getPwd().equals(password)) {
				// errorMsg = "密码不正确！";
				errorMsg = "登录失败，请重新登录！";
				return "failure";
			} else if (!pSysUser.getCurStatusCode().equals("01")) {
				// errorMsg = "用户已锁定或删除！";
				errorMsg = "登录失败，请重新登录！";
				return "failure";
			} // 四川盘龙需要绑定用户IP
			else if (!pSysUser.getIp().equals(ipAddr)) {
				errorMsg = "非法用户，请重新登录！";
				return "failure";
			} else {
				ServletContext application = super.getServletContext();
				Map onLineUsers = (Map) application.getAttribute("onLineUsers");
				if (onLineUsers == null) {
					onLineUsers = new HashMap();
					session.put("pSysUser", pSysUser);
					onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
					application.setAttribute("onLineUsers", onLineUsers);
					// 第一个登陆的用户没有登陆日志
					super.addUserLog("用户登录,用户工号:" + pSysUser.getStaffNo(),
							"login", "05");
					return "success";
				} else {
					if ("1".equals(PropertiesUtils.getProperty("loginFlag"))) {
						Object userLogin = onLineUsers.get(pSysUser
								.getStaffNo());
						if (userLogin == null) {
							onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
							application
									.setAttribute("onLineUsers", onLineUsers);
							session.put("pSysUser", pSysUser);
							super.addUserLog("用户登录,用户工号:"
									+ pSysUser.getStaffNo(), "login", "05");
							return "success";
						} else {
							errorMsg = "该用户已登陆";
							return "failure";
						}
					} else {
						onLineUsers.put(pSysUser.getStaffNo(), pSysUser);
						application.setAttribute("onLineUsers", onLineUsers);
						session.put("pSysUser", pSysUser);
						super.addUserLog("用户登录,用户工号:" + pSysUser.getStaffNo(),
								"login", "05");
						return "success";
					}
				}
			}

		} catch (Exception e) {
			errorMsg = "数据库或网络故障！";
			e.printStackTrace();
			return "failure";
		}

	}

	public void setiPSysUserManager(IPSysUserManager iPSysUserManager) {
		this.iPSysUserManager = iPSysUserManager;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		PSysUser loginUser = (PSysUser) session.getAttribute("pSysUser");

		// 从在线列表中删除用户名
		Map onLineUsers = (Map) application.getAttribute("onLineUsers");
		if (onLineUsers != null) {
			onLineUsers.remove(loginUser.getStaffNo());
			application.setAttribute("onLineUsers", onLineUsers);
		}

	}

}
