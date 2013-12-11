package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PRolePrivRela;
import com.nari.sysman.securityman.IPRolePrivRelaDao;
import com.nari.util.exception.DBAccessException;

public class PRolePrivRelaDaoImpl extends HibernateBaseDaoImpl<PRolePrivRela, Long> implements IPRolePrivRelaDao {

	public List<PRolePrivRela> findByRoleId(long roleId) throws DBAccessException {
		return this.findBy("roleId", roleId,"rolePrivRefId","asc");
	}

}
