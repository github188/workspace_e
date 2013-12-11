package com.nari.runman.abnormalhandle.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.flowhandle.DeviceExceptionBean;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.runman.abnormalhandle.DeviceExceptionDao;
import com.nari.runman.abnormalhandle.DeviceExceptionManager;
import com.nari.runman.abnormalhandle.EventNoCreateDao;
import com.nari.support.Page;
import com.nari.util.DeviceAbnormalUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class DeviceExceptionManagerImpl implements DeviceExceptionManager {

	private DeviceExceptionDao deviceExceptionDao;
	private EventNoCreateDao eventNoCreateDao;

	public void setEventNoCreateDao(EventNoCreateDao eventNoCreateDao) {
		this.eventNoCreateDao = eventNoCreateDao;
	}

	public void setDeviceExceptionDao(DeviceExceptionDao deviceExceptionDao) {
		this.deviceExceptionDao = deviceExceptionDao;
	}

	@Override
	public Page<DeviceExceptionBean> queryTmnlException(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception {
		try {
			return this.deviceExceptionDao.queryPageTmnlException(nodeType,
					nodeValue, userInfo, start, limit, startDate, endDate);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("根据左边树节点查询主站分析异常出错");
		}
	}

	@Override
	public List<DeviceExceptionBean> queryDeviceException(String tmnlExceptionId)
			throws Exception {
		try {
			return this.deviceExceptionDao.queryTmnlException(tmnlExceptionId);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("根据异常ID查询主站分析异常出错");
		}
	}

	@Override
	public long createEventNo(FEventSrc fEventSrc) throws Exception {
		try {
			fEventSrc.setFlowStatusCode(DeviceAbnormalUtil.NEW_EXCEPTION);
			fEventSrc.setEvnetTime(new Date());
			// 生成工单，获取工单号
			long eventNo = this.eventNoCreateDao.saveEventNo(fEventSrc);
			// 更新异常状态
			this.deviceExceptionDao.updateFlowStatusByExId(fEventSrc
					.getFromId(), fEventSrc.getFlowStatusCode());
			return eventNo;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单生成出错");
		}
	}

}
