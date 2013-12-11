package com.nari.baseapp.planpowerconsume;

import java.util.List;


import com.nari.orderlypower.WTmnlReject;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 
 * 创建时间：2009-12-23 下午05:27:55 
 * 描述：
 */

public interface IWTmnlRejectDao {
	/**
	 * 添加终端剔除信息
	 * @param wTmnlReject
	 * @throws DBAccessException
	 */
    public void save(WTmnlReject  wTmnlReject) throws DBAccessException;
	
    /**
     * 修改终端剔除信息
     * @param wTmnlReject
     * @throws DBAccessException
     */
	public void update(WTmnlReject  wTmnlReject) throws DBAccessException;
	
	/**
	 * 添加修改终端剔除信息
	 * @param wTmnlReject
	 * @throws DBAccessException
	 */
	public void saveOrUpdate(WTmnlReject  wTmnlReject) throws DBAccessException;
	
	/**
	 * 添加修改终端剔除信息(用于保存方案）
	 * @param wTmnlReject
	 * @throws DBAccessException
	 */
	public void saveOrUpdateByScheme(WTmnlReject  wTmnlReject) throws DBAccessException;
	
	/**
	 * 按属性过滤条件列表查找终端保电信息
	 * @param filters 过滤条件列表
	 * @return 终端保电信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<WTmnlReject> findBy(List<PropertyFilter> filters) throws DBAccessException;
}
