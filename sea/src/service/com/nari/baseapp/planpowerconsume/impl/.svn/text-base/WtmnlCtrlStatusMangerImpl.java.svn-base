package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.WtmnlCtrlStatusDao;
import com.nari.baseapp.planpowerconsume.WtmnlCtrlStatusManger;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.WTmnlCtrlStatus;

public class WtmnlCtrlStatusMangerImpl implements WtmnlCtrlStatusManger{
	private WtmnlCtrlStatusDao wtmnlCtrlStatusDao;

	public void setWtmnlCtrlStatusDao(WtmnlCtrlStatusDao wtmnlCtrlStatusDao) {
		this.wtmnlCtrlStatusDao = wtmnlCtrlStatusDao;
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusBusinessFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusBusinessFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusDownCtrlFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusDownCtrlFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusFactctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusFactctrlFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusFeectrlFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusFeectrlFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusMonPctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusMonPctrlFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusPwrctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusPwrctrlFlag(ctrlStatus);
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusTurnFlag(WTmnlCtrlStatus ctrlStatus) {
		wtmnlCtrlStatusDao.saveOrUpdateTmnlCtrlStatusTurnFlag(ctrlStatus);
	}

	@Override
	public WTmnlCtrlStatus createWTmnlCtrlStatus(CtrlParamBean bean) {
		WTmnlCtrlStatus c = new WTmnlCtrlStatus();
		c.setOrgNo(bean.getOrgNo());
		c.setConsNo(bean.getConsNo());
		c.setTmnlAssetNo(bean.getTmnlAssetNo());
		c.setTotalNo(bean.getTotalNo());
		return c;
	}
}
