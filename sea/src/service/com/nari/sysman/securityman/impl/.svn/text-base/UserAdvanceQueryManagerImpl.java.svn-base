package com.nari.sysman.securityman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.IUserAdvanceQueryDao;
import com.nari.sysman.securityman.IUserAdvanceQueryManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * DAO类 UserAdvanceQueryManagerImpl
 * 
 * @author zhangzhw 左边树用户列表高级查询DAO
 */
public class UserAdvanceQueryManagerImpl implements IUserAdvanceQueryManager {

	public IUserAdvanceQueryDao iUserAdvanceQueryDao;

	public Page<CConsRtmnl> findUserGrid(PSysUser username, long start,
			int limit, String con) throws Exception {
		try {
			return iUserAdvanceQueryDao.findCustmerByCon(con, start, limit,
					username);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_CONSRTMNLADVEXCEPT);
		}
	}

	/**
	 * 方法 findBureauList
	 * 
	 * @return 供电所列表
	 * @throws Exception
	 */
	@Override
	public List<OOrg> findBureauList(String username) throws Exception {
		try {
			return iUserAdvanceQueryDao.findBureauList(username);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_BUREAUEXCEPT);
		}
	}

	/**
	 * 方法 findBureauList
	 * 
	 * @return 供电所列表
	 * @throws Exception
	 */
	@Override
	public List<OOrg> findOrgNoList(String username) throws Exception {
		try {
			return iUserAdvanceQueryDao.findOrgNoList(username);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_ORGNOFINDEXCEPT);
		}
	}

	/**
	 * action 方法 　 subIdList
	 * 
	 * @return 字典列表　变电站
	 * @throws Exception
	 */
	public List subIdList(PSysUser pSysUser) throws Exception {

		return iUserAdvanceQueryDao.subIdList(pSysUser);

	}

	/**
	 * action 方法 　 lineIdList
	 * 
	 * @return 字典列表　线路
	 * @throws Exception
	 */
	public List lineIdList(PSysUser pSysUser) throws Exception {

		return iUserAdvanceQueryDao.lineIdList(pSysUser);

	}

	/**
	 * action 方法 　 tradeCodeList
	 * 
	 * @return 字典列表　行业
	 * @throws Exception
	 */
	public List tradeCodeList() throws Exception {

		return iUserAdvanceQueryDao.tradeCodeList();

	}

	/**
	 * action 方法 　 consTypeList
	 * 
	 * @return 字典列表　用户类型
	 * @throws Exception
	 */
	public List consTypeList() throws Exception {

		return iUserAdvanceQueryDao.consTypeList();

	}

	/**
	 * action 方法 　 elecTypeList
	 * 
	 * @return 字典列表　用电类别
	 * @throws Exception
	 */
	public List elecTypeList() throws Exception {

		return iUserAdvanceQueryDao.elecTypeList();

	}

	/**
	 * action 方法 　 capGradeList
	 * 
	 * @return 字典列表　用电容量等级
	 * @throws Exception
	 */
	public List capGradeList() throws Exception {

		return iUserAdvanceQueryDao.capGradeList();

	}

	/**
	 * action 方法 　 shiftNoList
	 * 
	 * @return 字典列表　班次
	 * @throws Exception
	 */
	public List shiftNoList() throws Exception {

		return iUserAdvanceQueryDao.shiftNoList();

	}

	/**
	 * action 方法 　 lodeAttrList
	 * 
	 * @return 字典列表　负荷性质
	 * @throws Exception
	 */
	public List lodeAttrList() throws Exception {

		return iUserAdvanceQueryDao.lodeAttrList();

	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　终端类型
	 * @throws Exception
	 */
	public List tmnlTypeList() throws Exception {

		return iUserAdvanceQueryDao.tmnlTypeList();

	}

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　采集方式
	 * @throws Exception
	 */
	public List collTypeList() throws Exception {

		return iUserAdvanceQueryDao.collTypeList();

	}

	public IUserAdvanceQueryDao getiUserAdvanceQueryDao() {
		return iUserAdvanceQueryDao;
	}

	public void setiUserAdvanceQueryDao(
			IUserAdvanceQueryDao iUserAdvanceQueryDao) {
		this.iUserAdvanceQueryDao = iUserAdvanceQueryDao;
	}

}
