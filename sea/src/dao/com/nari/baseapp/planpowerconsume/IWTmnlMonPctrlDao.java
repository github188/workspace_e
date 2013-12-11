package com.nari.baseapp.planpowerconsume;

import java.util.List;


import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 月电量定值控明细表Dao处理接口
 * @author jiangweichao
 */
public interface IWTmnlMonPctrlDao {
	
	/**
	 * 添加月电量定值控信息
	 * @param wTmnlMonPctrl
	 * @throws DBAccessException
	 */
	public void save(WTmnlMonPctrl wTmnlMonPctrl)throws DBAccessException;
	
	/**
	 * 根据过滤条件查询月电量定值控信息
	 * @param filters
	 * @return List<WTmnlMonPctrl>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WTmnlMonPctrl> findBy(List<PropertyFilter> filters)throws DBAccessException;
	
	/**
	 * 添加或更新月电量定值控信息
	 * @param wTmnlMonPctrl
	 * @throws DBAccessException
	 */
	public void saveOrUpdate(WTmnlMonPctrl wTmnlMonPctrl)throws DBAccessException;
	
	
	/**
	 * 保存或修改月电量定值控明细表，主要是添加方案适用
	 * @param monPctrl
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlMonPctrl wTmnlMonPctrl) throws DBAccessException;
	
}
