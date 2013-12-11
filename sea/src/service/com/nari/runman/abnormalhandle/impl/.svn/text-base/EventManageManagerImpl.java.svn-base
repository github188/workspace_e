package com.nari.runman.abnormalhandle.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.runman.abnormalhandle.EventInfoDao;
import com.nari.runman.abnormalhandle.EventManageManager;
import com.nari.runman.abnormalhandle.TmnlDataSaveORUpdateDao;
import com.nari.runman.abnormalhandle.VwDataTypeDao;
import com.nari.runman.abnormalhandle.VwProtocolCodeDao;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class EventManageManagerImpl implements EventManageManager {
	// 规约类型DAO
	private VwProtocolCodeDao vwProtocolCodeDao;

	// 规约数据类型DAO
	private VwDataTypeDao vwDataTypeDao;
	
	//终端数据更新、插入DAO
	private TmnlDataSaveORUpdateDao terminalSaveORUpdateDao;
	
	//事件信息查询DAO
	private EventInfoDao eventinfoDao;

	public void setEventinfoDao(EventInfoDao eventinfoDao) {
		this.eventinfoDao = eventinfoDao;
	}

	public void setTerminalSaveORUpdateDao(
			TmnlDataSaveORUpdateDao terminalSaveORUpdateDao) {
		this.terminalSaveORUpdateDao = terminalSaveORUpdateDao;
	}

	public void setVwProtocolCodeDao(VwProtocolCodeDao vwProtocolCodeDao) {
		this.vwProtocolCodeDao = vwProtocolCodeDao;
	}

	public void setVwDataTypeDao(VwDataTypeDao vwDataTypeDao) {
		this.vwDataTypeDao = vwDataTypeDao;
	}

	@Override
	public List<VwDataTypePBean> getDataTypeList(String proCode, String proNO)
			throws Exception {
		try {
			return this.vwDataTypeDao.findType(proCode, proNO);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询终端规约对应数据类型出错");
		}
	}

	@Override
	public List<VwProtocolCodeBean> getProCodeList() throws Exception {
		try {
			return this.vwProtocolCodeDao.findType();
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询规约类型出错");
		}
	}

	@Override
	public void saveORUpdateTerminal(List<TTmnlParam> ttmInfo) throws Exception {
		try {
			this.terminalSaveORUpdateDao.saveORUpdateTerminalInfo(ttmInfo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("终端事件报警数据保存出错");
		}
	}

	@Override
	public List<BProtocolEvent> getEventInfo(String proNo) throws Exception {
		try {
			return this.eventinfoDao.findPEInfo(proNo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("获取事件报警信息出错");
		}
	}

	@Override
	public void updateEventInfo(List<BProtocolEvent> eventInfo)
			throws Exception {
		try {
			this.eventinfoDao.updateEventInof(eventInfo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("事件报警级别更新出错");
		}
	}
}
