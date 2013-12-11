/**
 *创建人：陈建国
 *创建时间：20092009-11-15下午02:11:59	 
 *类描述：	
 */
package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.IOperatorManager;
import com.nari.sysman.securityman.IPSysUserDao;

/**
 * @author Administrator
 *
 */
public class OperatorManagerImpl implements IOperatorManager {

	IPSysUserDao iPSysUserDao ;
	

	public void setiPSysUserDao(IPSysUserDao iPSysUserDao) {
		this.iPSysUserDao = iPSysUserDao;
	}


	/**
	 * 添加用户信息
	 * @param pSysUser
	 */
	public void addPSysUser(PSysUser pSysUser) throws Exception{
		iPSysUserDao.save(pSysUser);
	}
	
	
	/**
	 * 查询所有用户信息
	 * @return
	 * @throws Exception
	 */
	public List <PSysUser> getAllPSysUser() throws Exception{
		return iPSysUserDao.findAll();
	}
}
