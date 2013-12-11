package com.nari.util;

import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.nari.privilige.PSysUser;



public class AuthLogin{
	
	/**
	 * @desc 用于认证任务下发输入密码是否正确
	 * @param password
	 * @return 1：表示正确，0:表示不正确
	 */
	public int isValid(String password){
		
		HttpSession session = WebContextFactory.get().getSession();
		PSysUser user = (PSysUser)session.getAttribute("pSysUser");
		if(null != password){
			if(null != user || !"".equals(user));{
				String pwd = user.getPwd();
				if(PasswordUtils.md5(password).equals(pwd)){
					return 1;
				}
			}
			return 0;
		}
		return 0;
	}
}
