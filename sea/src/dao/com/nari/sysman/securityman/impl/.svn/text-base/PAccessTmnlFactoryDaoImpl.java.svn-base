package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PAccessTmnlFactory;
import com.nari.sysman.securityman.IPAccessTmnlFactoryDao;
import com.nari.util.exception.DBAccessException;

public class PAccessTmnlFactoryDaoImpl extends HibernateBaseDaoImpl<PAccessTmnlFactory, Long> implements IPAccessTmnlFactoryDao {

	@Override
	public List<PAccessTmnlFactory> findByStaffNo(String staffNo) throws DBAccessException{
		return this.findBy("staffNo", staffNo);
	}

}
