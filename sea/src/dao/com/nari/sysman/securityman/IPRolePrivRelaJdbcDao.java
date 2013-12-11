package com.nari.sysman.securityman;

import com.nari.privilige.PRole;
import com.nari.util.exception.DBAccessException;

public interface IPRolePrivRelaJdbcDao {

	public void deleteByRoleId(Long roleId) throws DBAccessException;

	/**
	 * 方法 updateRoleMenu
	 * 
	 * @param menus
	 * @param pRole
	 * @return 更新角色信息和菜单权限
	 */
	public String updateRoleMenu(String menus, PRole pRole) throws Exception;

}
