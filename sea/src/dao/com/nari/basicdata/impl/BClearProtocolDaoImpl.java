package com.nari.basicdata.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.basicdata.BClearProtocol;
import com.nari.basicdata.BClearProtocolDao;
import com.nari.util.exception.DBAccessException;

public class BClearProtocolDaoImpl extends HibernateBaseDaoImpl<BClearProtocol, String> implements BClearProtocolDao{
	/****
	 * 通过queryType来查找透明规约的组合
	 * ***/
	@SuppressWarnings("unchecked")
	public List<BClearProtocol> findByQueryType(final Integer queryType) throws DBAccessException{
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Criteria c=s.createCriteria(BClearProtocol.class);
				c.add(Restrictions.or(Restrictions.eq("dataGroup", queryType), Restrictions.eq("dataGroup", 3)));
				c.addOrder(Order.asc("protocolNo"));
				return c.list();
			}
		});
	}
}
