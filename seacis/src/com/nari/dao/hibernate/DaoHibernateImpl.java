package com.nari.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class DaoHibernateImpl extends HibernateDaoSupport implements DaoHibernate {
	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @return
	 */
	public Object findDAOInfoObject(String hql) {
		Object objectDAO = new Object();
		List l = getHibernateTemplate().find(hql);
		if (l == null || l.size() == 0)
			return null;
		else {
			objectDAO = l.get(0);
			return objectDAO;
		}
	}

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public Object findDAOInfoObject(String hql, Object object) {
		Object objectDAO = new Object();
		List l = getHibernateTemplate().find(hql, object);
		if (l == null || l.size() == 0)
			return null;
		else {
			objectDAO = l.get(0);
			return objectDAO;
		}
	}

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public Object findDAOInfoObject(String hql, Object[] object) {
		Object objectDAO = new Object();
		List l = getHibernateTemplate().find(hql, object);
		if (l == null || l.size() == 0)
			return null;
		else {
			objectDAO = l.get(0);
			return objectDAO;
		}

	}

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @return
	 */
	public List findDAOInfoList(String hql) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql);
		return l;
	}

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public List findDAOInfoList(String hql, Object object) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql, object);
		return l;
	}

	/**
	 * 查找获得信息
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public List findDAOInfoList(String hql, Object[] object) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql, object);
		return l;
	}

	/**
	 * 记录数
	 * 
	 * @param hql
	 * @return
	 */
	public int getDAOInfoCount(String hql) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql);
		return l.size();
	}

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public int getDAOInfoCount(String hql, Object object) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql, object);
		return l.size();
	}

	/**
	 * 获得记录数
	 * 
	 * @param hql
	 * @param object
	 * @return
	 */
	public int getDAOInfoCount(String hql, Object[] object) {
		List l = new ArrayList();
		l = getHibernateTemplate().find(hql, object);
		return l.size();
	}

	/**
	 * 分页显示信息
	 * 
	 * @param hql
	 * @param start
	 * @param pageSize
	 * @return List
	 */
	public List getDAOInfoList(String hql, int start, int pageSize) {
		List list = new ArrayList();
		try {
			Query query = this.getSession().createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
			list = query.list();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 添加信息
	 * 
	 * @param object
	 * @return long
	 */
	public Integer addDAOInfoReturnId(Object object) {
		Serializable addresslistId = getHibernateTemplate().save(object);
		return ((Integer) addresslistId);
	}

	/**
	 * 添加信息
	 */
	public void addDAOInfo(Object object) {
		getHibernateTemplate().save(object);
	}

	/**
	 * 修改信息
	 * 
	 * @param object
	 */
	public boolean modifyDAOInfo(Object object) {
		getHibernateTemplate().update(object);
		return true;
	}

	public boolean modifyDAOInfo1(String hql) {
		getHibernateTemplate().update(hql);
		return true;
	}

	/**
	 * 删除信息
	 * 
	 * @param object
	 */
	public boolean deleteDAOInfo(Object object) {
		getHibernateTemplate().delete(object);
		return true;
	}

	/**
	 * 删除信息
	 * 
	 * @param hql
	 */
	public boolean deleteDAOInfo(String hql) {
		try {
			getSession().createQuery(hql).executeUpdate();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return true;
	}
}