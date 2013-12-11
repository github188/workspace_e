package com.nari.sysman.securityman;

import com.nari.action.baseaction.BaseAction;

public class LogoutAction extends BaseAction{
	public String logout() {
		super.getSession().invalidate();
		return "success";
	}
}
