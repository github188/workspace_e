package com.nari.dao.hibernate;

import java.util.List;

@SuppressWarnings("unchecked")
public interface DaoHibernate {
	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @return
	 */
	Object findDAOInfoObject(String hql);

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	Object findDAOInfoObject(String hql, Object object);

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	Object findDAOInfoObject(String hql, Object[] object);

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @return
	 */
	List findDAOInfoList(String hql);

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	List findDAOInfoList(String hql, Object object);

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	List findDAOInfoList(String hql, Object[] object);

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @return
	 */
	int getDAOInfoCount(String hql);

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	int getDAOInfoCount(String hql, Object object);

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	int getDAOInfoCount(String hql, Object[] object);

	/**
	 * 分页显示信息
	 * 
	 * @param hql
	 * @param start
	 * @param pageSize
	 * @return List
	 */
	List getDAOInfoList(String hql, int start, int pageSize);

	/**
	 * 添加信息
	 * 
	 * @param object
	 * @return int
	 */
	Integer addDAOInfoReturnId(Object object);

	void addDAOInfo(Object object);

	/**
	 * 修改信息
	 * 
	 * @param object
	 */
	boolean modifyDAOInfo(Object object);

	boolean modifyDAOInfo1(String hql);

	/**
	 * 删除信息
	 * 
	 * @param object
	 */
	boolean deleteDAOInfo(Object object);

	/**
	 * 删除信息
	 * 
	 * @param hql
	 */
	boolean deleteDAOInfo(String hql);
}