package com.nari.sysman.securityman.impl;

import org.springframework.dao.DataAccessException;

import com.nari.oranization.OOrgDao;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;
import com.nari.sysman.securityman.IOperatorsDao;
import com.nari.sysman.securityman.IOperatorsManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

public class OperatorsManagerImpl implements IOperatorsManager {

	public IOperatorsDao iOperatorsDao;
	public OOrgDao oOrgDao;

	@Override
	public Page<PSysUserJdbc> findPage(long start, long limit, PSysUser pSysUser)
			throws Exception {
		try {
			// 处理客户中心本部的权限级别放大的问题

			String accessOrg = pSysUser.getOrgNo();
			OOrg oorg = this.oOrgDao.findById(accessOrg);
			if (!oorg.getOrgType().equals(pSysUser.getAccessLevel())) {
				accessOrg = oorg.getPOrgNo();
			}

			return iOperatorsDao.findPage(start, limit, accessOrg);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYOPERATORSEXCEPTION);
		}
	}

	@Override
	public Page<PSysUserJdbc> findPage(long start, long limit,
			PSysUser pSysUserQuery, PSysUser pSysUser) throws Exception {
		try {
			// 处理客户中心本部的权限级别放大的问题

			String accessOrg = pSysUser.getOrgNo();
			OOrg oorg = this.oOrgDao.findById(accessOrg);
			if (!oorg.getOrgType().equals(pSysUser.getAccessLevel())) {
				accessOrg = oorg.getPOrgNo();
			}

			return iOperatorsDao.findPage(start, limit, pSysUserQuery,accessOrg);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYOPERATORSEXCEPTION);
		}
	}

	@Override
	public String saveOrUpdate(PSysUser pSysUser, String orgs, String facs,
			String roles, String consTypes) throws Exception {
		try {
			return iOperatorsDao.saveOrUpdate(pSysUser, orgs, facs, roles,
					consTypes);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_SAVEUPDATEOPERATOR);
		}
	}

	@Override
	public boolean deletePSysUser(String staffNo) throws Exception {

		try {
			return iOperatorsDao.deletePSysUser(staffNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_DELETEOPERATORSEXCEPTION);
		}
	}

	public IOperatorsDao getiOperatorsDao() {
		return iOperatorsDao;
	}

	public void setiOperatorsDao(IOperatorsDao iOperatorsDao) {
		this.iOperatorsDao = iOperatorsDao;
	}

	public OOrgDao getoOrgDao() {
		return oOrgDao;
	}

	public void setoOrgDao(OOrgDao oOrgDao) {
		this.oOrgDao = oOrgDao;
	}

}
