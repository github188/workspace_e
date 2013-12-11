package com.nari.runman.runstatusman.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.logofsys.LMasterStationResBean;
import com.nari.runman.runstatusman.LMasterStationResDao;
import com.nari.runman.runstatusman.LMasterStationResManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class LMasterStationResManagerImpl implements LMasterStationResManager {

	private static Logger log = Logger.getLogger(LMasterStationResManagerImpl.class);
	private static final String CJJ_CLUSTER = "采集集群";
	private static final String WGJ_CLUSTER = "网关集群";
	private static final String JKFW_CLUSTER = "接口集群";
	private static final String SJK_CLUSTER = "数据库集群";
	private static final String WEB_CLUSTER = "web集群";
	
	private LMasterStationResDao lMasterStationResDao;
	
	public void setlMasterStationResDao(LMasterStationResDao lMasterStationResDao) {
		this.lMasterStationResDao = lMasterStationResDao;
	}



	@Override
	public List<LMasterStationResBean> findLMasterStationResCjj(
			Date msDate) throws DBAccessException {
		try {
			List<LMasterStationResBean> list = lMasterStationResDao.findLMasterStationRes(msDate, CJJ_CLUSTER);
			return list;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<LMasterStationResBean> findLMasterStationResWgj(
			Date msDate) throws DBAccessException {
		try {
			List<LMasterStationResBean> list = lMasterStationResDao.findLMasterStationRes(msDate, WGJ_CLUSTER);
			return list;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<LMasterStationResBean> findLMasterStationResJkfw(
			Date msDate) throws DBAccessException {
		try {
			List<LMasterStationResBean> list = lMasterStationResDao.findLMasterStationRes(msDate, JKFW_CLUSTER);
			return list;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<LMasterStationResBean> findLMasterStationResSjk(
			Date msDate) throws DBAccessException {
		try {
			List<LMasterStationResBean> list = lMasterStationResDao.findLMasterStationRes(msDate, SJK_CLUSTER);
			return list;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<LMasterStationResBean> findLMasterStationResWeb(
			Date msDate) throws DBAccessException {
		try {
			List<LMasterStationResBean> list = lMasterStationResDao.findLMasterStationRes(msDate, WEB_CLUSTER);
			return list;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List <Object> getTermTaskInfo(){
		try {
			return lMasterStationResDao.findTermTaskInfo();
		} catch (Exception e) {
			log.info("与coherence服务器无法通信!");
			e.printStackTrace();
		}
		return null;
	}
}
