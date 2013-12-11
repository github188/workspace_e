package com.nari.runman.abnormalhandle.impl;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.flowhandle.FEventSrc;
import com.nari.runman.abnormalhandle.EventNoCreateDao;
import com.nari.util.exception.DBAccessException;

public class EventNoCreateDaoImpl extends HibernateBaseDaoImpl<FEventSrc, String> implements EventNoCreateDao {

	@Override
	public long saveEventNo(FEventSrc fEventSrc) throws DBAccessException {
		this.saveOrUpdate(fEventSrc);
		return fEventSrc.getEventId();
	}

}
