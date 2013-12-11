package com.nari.sysman.templateman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.support.Page;
import com.nari.sysman.templateman.TmnlTaskConfigDao;
import com.nari.sysman.templateman.TmnlTaskConfigManager;
import com.nari.terminalparam.ITmnlProtSentSetupBean;
import com.nari.terminalparam.ITmnlTaskSetupBean;
import com.nari.terminalparam.TTaskTemplateBean;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class TmnlTaskConfigManagerImpl implements TmnlTaskConfigManager {
	private TmnlTaskConfigDao tmnlTaskConfigDao;

	public void setTmnlTaskConfigDao(TmnlTaskConfigDao tmnlTaskConfigDao) {
		this.tmnlTaskConfigDao = tmnlTaskConfigDao;
	}
	
	@Override
	public String saveOrUpdate(ITmnlProtSentSetupBean bean) throws DBAccessException{
		try{
			 return this.tmnlTaskConfigDao.saveOrUpdate(bean);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	
	public String saveOrUpdate_1(ITmnlTaskSetupBean beanUser) throws DBAccessException{
		try{
			return this.tmnlTaskConfigDao.saveOrUpdate_1(beanUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	};
	
	
	@Override
	public void deleteRule(String protSendSetupId) throws DBAccessException{
		try{
			this.tmnlTaskConfigDao.deleteRule(protSendSetupId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public void deleteRuleH(String taskSetupId) throws DBAccessException{
		try{
			this.tmnlTaskConfigDao.deleteRuleH(taskSetupId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	
	@Override
	public Page<ITmnlProtSentSetupBean> findTmnlTask(long start, int limit)
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findTmnlTask(start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<ITmnlTaskSetupBean> findUserTask(long start, int limit)
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findUserTask(start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<TTaskTemplateBean> findTTaskT(long start, int limit) throws DBAccessException {
		try{
			return this.tmnlTaskConfigDao.findTTaskT(start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlProtSentSetupBean> findTmnlFactory()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findTmnlFactory();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlProtSentSetupBean> findTmnlModel()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findTmnlModel();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlProtSentSetupBean> findTmnlType()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findTmnlType();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlProtSentSetupBean> findCollMode()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findCollMode();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
//	@Override
//	public List<ITmnlProtSentSetupBean> findSendUp()
//			throws DBAccessException {
//		try {
//			return this.tmnlTaskConfigDao.findSendUp();
//		} catch (DataAccessException e) {
//			throw new DBAccessException(BaseException.processDBException(e));
//		}
//	}
	
	@Override
	public List<ITmnlProtSentSetupBean> findProtocol()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findProtocol();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlTaskSetupBean> findConsType()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findConsType();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<ITmnlTaskSetupBean> findCapGrade()
			throws DBAccessException {
		try {
			return this.tmnlTaskConfigDao.findCapGrade();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
}
