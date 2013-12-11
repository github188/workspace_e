package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.support.PropertyFilter;
import com.nari.terminalparam.RUserGroup;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2010-1-11 下午04:19:08 
 * 描述：备选用户组（普通群组）Dao接口
 */

public interface IRUserGroupDao {
	/**
	 * 添加普通群组
	 * @param tTmnlGroup 群组信息
	 * @throws DBAccessException 数据库异常
	 */
	public void save(RUserGroup rUserGroup) throws DBAccessException;
	
	/**
	 * 修改普通群组
	 * @param rUserGroup 普通群组
	 * @throws DBAccessException
	 */
	public void update(RUserGroup rUserGroup) throws DBAccessException;
	
	/**
	 * 删除普通群组
	 * @param groupNo 群组编号
	 * @throws DBAccessException
	 */
	public void delete(String groupNo)throws DBAccessException;
	
	/**
	 * 按属性过滤条件列表查找普通群组信息
	 * @param filters 过滤条件列表
	 * @return 群组信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<RUserGroup> findBy(List<PropertyFilter> filters) throws DBAccessException;
}
