package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-15 上午08:48:24 
 * 描述：
 */

public interface IWTmnlPaulPowerDao {
	/**
	 * 新增终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void save(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException;
	
	/**
	 * 修改终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void update(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException;
	/**
	 * 新增或修改终端保电信息
	 * @param tTaskTemplate 终端保电信息
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdate(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException;
	
	/**
	 * 新增或修改终端保电信息
	 * @param tTaskTemplate 终端保电信息
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdateByScheme(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException;
	
	/**
	 * 按属性过滤条件列表查找终端保电信息
	 * @param filters 过滤条件列表
	 * @return 终端保电信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<WTmnlPaulPower> findBy(List<PropertyFilter> filters) throws DBAccessException;
}
