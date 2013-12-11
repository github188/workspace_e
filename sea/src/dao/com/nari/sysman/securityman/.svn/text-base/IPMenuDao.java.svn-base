package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PMenu;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * s 接口 IPMenuDao
 * 
 * @author zhangzhw
 * @describe 对菜单权限进行访问的DAO接口
 */
public interface IPMenuDao {

	/**
	 * 方法 findByUpid
	 * 
	 * @param PMenuNo
	 * @param pSysUser
	 * @return 根据上级菜单取得
	 * @throws DBAccessException
	 */
	public List<PMenu> findByUpid(String PMenuNo, PSysUser pSysUser);

	/**
	 * 方法 findAllMenu
	 * 
	 * @return 取得所有菜单
	 */
	public List<PMenu> findAllMenu();

	/**
	 * 方法 findByRole
	 * 
	 * @return 根据角色权限取得菜单
	 */
	public List<PMenu> findByRole(String roleId);

}
