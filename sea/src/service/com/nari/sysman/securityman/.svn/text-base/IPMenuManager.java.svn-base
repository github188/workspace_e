package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PMenu;
import com.nari.privilige.PSysUser;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.DBAccessException;

/**
 * 接口 IPMenuManager
 *
 * @author zhangzhw
 * @describe 菜单管理服务接口
 */
public interface IPMenuManager {

	/**
	 * 通过上级菜单编码取得菜单列表
	 *
	 * @param PMenuNo
	 * @return 系统菜单列表
	 */
	public List<PMenu> findByUpid(String PMenuNo,PSysUser pSysUser) throws Exception;
	
	/**
	 * @param node 
	 * @param roleId 
	 * @param 
	 * @return 系统菜单
	 * @throws DBAccessException 
	 */
	public List<TreeNodeChecked> findMenu(String node, String roleId,PSysUser pSysUser) throws Exception;

}
