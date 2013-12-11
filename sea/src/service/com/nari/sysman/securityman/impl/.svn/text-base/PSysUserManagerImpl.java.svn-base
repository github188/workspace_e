package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.IPSysUserDao;
import com.nari.sysman.securityman.IPSysUserManager;
import com.nari.util.exception.DBAccessException;

/**
 * 类 PSysUserManagerImpl
 *
 * @author zhangzhw 用户
 */
public class PSysUserManagerImpl implements IPSysUserManager {

	private IPSysUserDao iPSysUserDao;

	public void setiPSysUserDao(IPSysUserDao iPSysUserDao) {
		this.iPSysUserDao = iPSysUserDao;
	}


	public Page<PSysUser> findPage(Page<PSysUser> page) throws DBAccessException {
		return this.iPSysUserDao.findPage(page);
	}


	public Page<PSysUser> findPage(Page<PSysUser> page, String propertyName, Object value, MatchType matchType) throws DBAccessException {
		return this.iPSysUserDao.findPage(page, propertyName, value, matchType);
	}


	public Page<PSysUser> findPage(Page<PSysUser> page, List<PropertyFilter> filters) throws DBAccessException {
		return this.iPSysUserDao.findPage(page, filters);
	}

	/**
	 * @描述 系统登录
	 * @param staffNo 工号
	 * @param password 密码
	 * @return
	 * @throws DBAccessException
	 */
	public PSysUser login(String staffNo,String password) throws Exception{
		return iPSysUserDao.findByStaffNoAndPWD(staffNo, password);
	}

}
