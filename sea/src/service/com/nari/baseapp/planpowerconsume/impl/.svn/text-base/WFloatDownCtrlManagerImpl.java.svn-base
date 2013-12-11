package com.nari.baseapp.planpowerconsume.impl;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.WFloatDownCtrlDao;
import com.nari.baseapp.planpowerconsume.WFloatDownCtrlManager;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class WFloatDownCtrlManagerImpl implements WFloatDownCtrlManager {
	WFloatDownCtrlDao wFloatDownCtrlDao;

	public void setWFloatDownCtrlDao(WFloatDownCtrlDao floatDownCtrlDao) {
		wFloatDownCtrlDao = floatDownCtrlDao;
	}

	@Override
	public void save(WFloatDownCtrl downCtrl) throws DBAccessException {
		try {
			this.wFloatDownCtrlDao.save(downCtrl);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
