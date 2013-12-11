package com.nari.runman.abnormalhandle.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nari.flowhandle.FEventClose;
import com.nari.flowhandle.FEventFlow;
import com.nari.flowhandle.FEventShield;
import com.nari.flowhandle.FEventSrc;
import com.nari.runman.abnormalhandle.DeviceAbnormalHibernateDao;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

public class DeviceAbnormalHibernateDaoImpl extends HibernateDaoSupport
		implements DeviceAbnormalHibernateDao {

	@Override
	public void updateEventSrc(final FEventSrc fEventSrc)
			throws DBAccessException {
//		this.getHibernateTemplate().saveOrUpdate(fEventSrc);
		if (null == fEventSrc) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.update(fEventSrc);
				return null;
			}
		});
	}

	@Override
	public void insertEventFlow(final FEventFlow fEventFlow)
			throws DBAccessException {
		if (null == fEventFlow) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.saveOrUpdate(fEventFlow);
				return null;
			}
		});
	}

	@Override
	public void insertEventClose(final FEventClose fEventClose)
			throws DBAccessException {
		if (null == fEventClose) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.saveOrUpdate(fEventClose);
				return null;
			}
		});
	}

	@Override
	public void insertEventShield(final FEventShield fEventShield) throws DBAccessException {
		if (null == fEventShield) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.saveOrUpdate(fEventShield);
				return null;
			}
		});
	}
}
