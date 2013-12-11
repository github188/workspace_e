/**
 *创建人：陈建国
 *创建时间：20092009-11-15下午02:09:40	 
 *描述：	
 */
package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * @author Administrator
 *
 */
public interface IOperatorManager {

	/**
	 * 添加用户信息
	 * @param pSysUser
	 */
	public void addPSysUser(PSysUser pSysUser) throws Exception;
	
	
	/**
	 * 查询所有用户信息
	 * @return
	 * @throws Exception
	 */
	public List <PSysUser> getAllPSysUser() throws Exception;
}
