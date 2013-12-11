package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 方案控制表操作Dao实现类
 * 
 * @author 姜炜超
 */
public class WCtrlSchemeDaoImpl extends
		HibernateBaseDaoImpl<WCtrlScheme, String> implements IWCtrlSchemeDao {

	@SuppressWarnings("unchecked")
	public Page<WCtrlScheme> findAllWCtrlScheme(Page page)
			throws DBAccessException {
		return findPage(page);
	}
	
	public void disableCtrlScheme(List<Integer> ids) throws DBAccessException {
		String sql = "update"+ entityClass.getName()+" set isValid=0 where " +
				"ctrlSchemeId in (:ids)";
		Query q = getSession().createQuery(sql);
		q.setParameterList("ids", ids);
		q.executeUpdate();
	}

	public void enableCtrlScheme(List<Integer> ids)throws DBAccessException {
		String sql = "update "+entityClass.getName()+" set isValid=1 where " +
				"ctrlSchemeId in (:ids)";
		Query q = getSession().createQuery(sql);
		q.setParameterList("ids", ids);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WCtrlScheme> findBySchemeTypeAndStaffNo(String schemeType,
			String staffNo) throws DBAccessException {
		String hql = "from WCtrlScheme w where w.ctrlSchemeType = ? and w.staffNo = ?";
		return getHibernateTemplate().find(hql, new Object[]{schemeType, staffNo});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<WCtrlScheme> findPage(WCtrlScheme scheme,long start, int limit)
			throws DBAccessException {
		String hql = "from WCtrlScheme w where 1=1\n";
		List<Object> paramList = new ArrayList<Object>();
		
		if(scheme.getIsValid()!=null) {
			hql +=" and w.isValid =?\n";
			paramList.add(scheme.getIsValid());
		}
		if(scheme.getLimitType()!=null) {
			hql +=" and w.limitType =?\n";
			paramList.add(scheme.getLimitType());
		} else {
			hql += " and w.limitType in('01','02','04','06')\n";
		}
		if (scheme.getCtrlDateStart() != null) {
			hql +=" and w.ctrlDateStart =?\n";
			paramList.add(scheme.getCtrlDateStart());
		}
		if (scheme.getCtrlDateEnd() != null) {
			hql +=" and w.ctrlDateEnd =?\n";
			paramList.add(scheme.getCtrlDateEnd());
		}
		hql += " and rownum> ? and rownum < ?";
		List<WCtrlScheme> list = getHibernateTemplate().find(hql,paramList.toArray());
		Page<WCtrlScheme> page = new Page<WCtrlScheme>();
		page.setResult(list);
		page.setTotalCount(list.size());
		return null;
	}
}
