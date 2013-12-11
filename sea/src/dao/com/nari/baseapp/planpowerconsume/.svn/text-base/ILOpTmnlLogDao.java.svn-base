package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.logofsys.LOpTmnlLog;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-22 下午01:30:13 
 * 描述：操作终端日志Dao接口
 */

public interface ILOpTmnlLogDao {
    
	/**
	 * 添加操作终端日志
	 * @param lOpTmnlLog 操作终端日志
	 */
	public void save(LOpTmnlLog lOpTmnlLog) throws DBAccessException;
	
	/**
	 * 修改操作终端日志
	 * @param lOpTmnlLog 操作终端日志
	 */
	public void update(LOpTmnlLog lOpTmnlLog) throws DBAccessException;
	
	/**
	 * 按照条件查询操作终端日志
	 * @param lOpTmnlLog 操作终端日志
	 * @return 
	 */
	public List<LOpTmnlLog>findBy(List<PropertyFilter> filters)throws DBAccessException;
}
