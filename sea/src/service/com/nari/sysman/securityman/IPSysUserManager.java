package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;

public interface IPSysUserManager {

	/**
	 * 分页查询所有用户
	 * @param page 分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page) throws DBAccessException;

	/**
	 * 按属性过滤条件分页查找对象，支持多种匹配方式。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page, final String propertyName, final Object value, final MatchType matchType) throws DBAccessException;

	/**
	 * 按属性过滤条件列表分页查找对象。
	 * @param page 分页参数
	 * @param filters 过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	public Page<PSysUser> findPage(final Page<PSysUser> page, final List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * @描述 系统登录
	 * @param staffNo 工号
	 * @param password 密码
	 * @return
	 * @throws DBAccessException
	 */
	public PSysUser login(String staffNo,String password) throws Exception;

}
