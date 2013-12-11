package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WTmnlBusiness;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 营业报停控明细表Dao处理接口
 * @author jiangweichao
 */
public interface IWTmnlBusinessDao {
	
	/**
	 * 根据过滤条件查询营业报停控信息
	 * @param filters
	 * @return List<WTmnlBusiness>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WTmnlBusiness> findBy(List<PropertyFilter> filters)throws DBAccessException;
	
	/**
	 * 添加营业报停控信息
	 * @param busi
	 * @throws DBAccessException
	 */
	public void save(WTmnlBusiness busi)throws DBAccessException;
	
	/**
	 * 添加或更新营业报停控信息
	 * @param wTmnlBusiness
	 * @throws DBAccessException
	 */
	public void saveOrUpdate(WTmnlBusiness busi)throws DBAccessException;
	
	/**
	 * 添加或更新营业报停控信息（用于添加更新方案）
	 * @param wTmnlBusiness
	 * @throws DBAccessException
	 */
	public void saveOrUpdateByScheme(WTmnlBusiness busi)throws DBAccessException;
}
