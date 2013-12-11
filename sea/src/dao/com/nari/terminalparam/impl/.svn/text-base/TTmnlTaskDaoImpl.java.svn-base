package com.nari.terminalparam.impl;

import org.springframework.dao.DataAccessException;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.terminalparam.TTmnlTask;
import com.nari.terminalparam.TTmnlTaskDao;
import com.nari.terminalparam.TTmnlTaskId;
import com.nari.util.exception.DBAccessException;

public class TTmnlTaskDaoImpl extends HibernateBaseDaoImpl<TTmnlTask,TTmnlTaskId> implements TTmnlTaskDao {

	public TTmnlTask findById(TTmnlTaskId tTmnlTaskId) throws DBAccessException {
		try {
			return (TTmnlTask)this.getHibernateTemplate().get(TTmnlTask.class, tTmnlTaskId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	};
}
