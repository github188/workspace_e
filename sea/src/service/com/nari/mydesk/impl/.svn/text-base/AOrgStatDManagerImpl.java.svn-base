package com.nari.mydesk.impl;

import java.util.List;

import com.nari.mydesk.AOrgStatD;
import com.nari.mydesk.AOrgStatDManager;
import com.nari.mydesk.ElecOrderDTO;
import com.nari.mydesk.MainCurve;
import com.nari.util.exception.DBAccessException;

public class AOrgStatDManagerImpl implements AOrgStatDManager{
	private AOrgStatDDaoImpl aOrgStatDDaoImpl;
	
	public AOrgStatDDaoImpl getaOrgStatDDaoImpl() {
		return aOrgStatDDaoImpl;
	}

	public void setaOrgStatDDaoImpl(AOrgStatDDaoImpl aOrgStatDDaoImpl) {
		this.aOrgStatDDaoImpl = aOrgStatDDaoImpl;
	}

	@Override
	public List<AOrgStatD> getAOrgStatDByOrgNo(String orgNo, String orgType)
			throws DBAccessException {
		return this.aOrgStatDDaoImpl.getAOrgStatDByOrgNo(orgNo, orgType);
	}

	@Override
	public List<AOrgStatD> getAOrgStatDBystaffNo(String staffNo,String orgType)
			throws DBAccessException {
		// TODO Auto-generated method stub
		return this.aOrgStatDDaoImpl.getAOrgStatDBystaffNo(staffNo,orgType);
	}

	@Override
	public List<MainCurve> getMainCurve(String orgNo, String orgType)
			throws DBAccessException {
		return this.aOrgStatDDaoImpl.getMainCurve(orgNo, orgType);
	}
	
	public List<AOrgStatD> getAOrgStatDOrgType(String orgNo)throws DBAccessException{
		return this.aOrgStatDDaoImpl.getAOrgStatDOrgType(orgNo);
	}

	
}