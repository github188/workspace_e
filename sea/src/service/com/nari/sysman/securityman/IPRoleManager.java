/**
 * 作者:姜海辉
 * 创建时间：2009-11-16 上午11:20:35
 * 描述：
 */
package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PRole;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.DBAccessException;

public interface IPRoleManager {

	/**
	 * 增加角色信息
	 * @param pRole
	 */
	public void addPRole(PRole pRole)throws Exception;

	/**
	 * 删除角色信息
	 * @param pRole
	 */
	public void deletePRole(PRole pRole)throws Exception;

	/**
	 * 根据角色标识删除角色信息
	 * @param roleId
	 */
	public void deletePRole(long roleId)throws Exception;

	/**
	 * 修改角色信息
	 * @param pRole
	 */
	public void updatePRole(PRole pRole)throws Exception;
	
	/**
	 * 方法  updateRoleMenu
	 * @param menus
	 * @param pRole
	 * @return 更新角色信息 成功返回 success 其它返回错误信息
	 * @throws Exception
	 */
	public String updateRoleMenu(String menus,PRole pRole) throws Exception;

	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<PRole> getAllPRole()throws Exception;

	/**
	 * 根据角色标识查询用户信息
	 * @param roleId
	 * @return
	 */
	public PRole getAllPRole(String roleId)throws Exception;
	
	/**
	 * 根据角色名称查询用户信息
	 * @param roleId
	 * @return
	 */
	public List<PRole> findByRoleDesc(String roleDesc) throws Exception;

	/**
	 * 分页查询所有角色
	 * @param page 分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page) throws DBAccessException;

	/**
	 * 按属性过滤条件分页查找对象，支持多种匹配方式。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page, final String propertyName, final Object value, final MatchType matchType) throws DBAccessException;

	/**
	 * 按属性过滤条件列表分页查找对象。
	 * @param page 分页参数
	 * @param filters 过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PRole> findPage(final Page<PRole> page, final List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 修改角色菜单。
	 * @param roleId 角色ID
	 * @param roleMenus 角色菜单
	 * @throws Exception 
	 */
	public void updateRoleMenu(String roleMenus, Long roleId) throws Exception;
	
	/**
	 * 角色树。
	 * @param staffNo 
	 * @throws Exception 
	 */
	public List<TreeNodeChecked> findRoleTree(String staffNo) throws Exception;

}
