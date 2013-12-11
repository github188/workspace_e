package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PRolePrivRela;
import com.nari.util.exception.DBAccessException;

public interface IPRolePrivRelaDao {
	
	public List<PRolePrivRela> findByRoleId(long roleId) throws DBAccessException;

	public void save(PRolePrivRela pRolePrivRela) throws DBAccessException;
}
