package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PUserRoleRela;
import com.nari.sysman.securityman.IPUserRoleRelaDao;
import com.nari.util.exception.DBAccessException;

public class PUserRoleRelaDaoImpl extends HibernateBaseDaoImpl<PUserRoleRela, String> implements IPUserRoleRelaDao {

	@Override
	public List<PUserRoleRela> findByStaffNo(String staffNo) throws DBAccessException{
		// TODO Auto-generated method stub
		return this.findBy("staffNo", staffNo,"userRoleRefId","asc");
	}

}
