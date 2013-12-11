package com.nari.oranization;

import java.util.List;

import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

public interface OOrgDao {

	/**
	 * 接口 方法findBy 继承自 HibernatBaseDao
	 * 
	 * @param property
	 * @param value
	 * @param sorPro
	 * @param sort
	 * @return
	 */
	public List<OOrg> findBy(String propertyName, Object value, String orderBy, String order) throws DBAccessException;

	/**
	 * 根据orgNO查询
	 * 
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public OOrg findById(String orgNo) throws DBAccessException;

	/**
	 * 根据上级orgNO查询
	 * 
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<OOrg> findByPId(String orgNo) throws DBAccessException;

	/**
	 * 方法 findAll
	 * 
	 * @return 所有供电单位列表
	 * @throws DBAccessException
	 */
	public List<OOrg> findAll() throws DBAccessException;
}
