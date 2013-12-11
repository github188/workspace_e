package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;


import com.nari.baseapp.planpowerconsume.IWTmnlSchemePublicDao;
import com.nari.baseapp.planpowerconsume.IWTmnlSchemePublicManager;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class WTmnlSchemePublicManagerImpl implements IWTmnlSchemePublicManager {
	IWTmnlSchemePublicDao iWTmnlSchemePublicDao;
	
	public void setiWTmnlSchemePublicDao(IWTmnlSchemePublicDao iWTmnlSchemePublicDao) {
		this.iWTmnlSchemePublicDao = iWTmnlSchemePublicDao;
	}

	public void updateAboutCtrlScheme(List ctrlSchemeIds) throws DBAccessException {
		iWTmnlSchemePublicDao.updateAboutCtrlScheme(ctrlSchemeIds);
	}

	@Override
	public void deleteCtrlSchemeUser(Long ctrlSchemeId, List userNos)  throws DBAccessException {
		iWTmnlSchemePublicDao.deleteCtrlSchemeUser(ctrlSchemeId, userNos);
	}


	@Override
	public <T> Page<T> findCtrlSchemeUser(Long ctrlSchemeId, long start,
			int limit)   throws DBAccessException{
		return iWTmnlSchemePublicDao.findCtrlSchemeUser(ctrlSchemeId, start, limit);
	}

	@Override
	public Page findAllUsers(String staffNo, Long ctrlSchemeId, long start,
			int limit) throws DBAccessException {
		return iWTmnlSchemePublicDao.findAllUsers(staffNo, ctrlSchemeId, start, limit);
	}
	

}
