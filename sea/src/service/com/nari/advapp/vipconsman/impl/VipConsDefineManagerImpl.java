package com.nari.advapp.vipconsman.impl;

import com.nari.advapp.vipconsman.VipConsDefineManager;
import com.nari.advapp.vipconsman.VipConsManagerDao;
import com.nari.advapp.vipconsman.VipConsManagerDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 
 * @author ChunMingLi
 * @since  2010-6-3
 *
 */
public class VipConsDefineManagerImpl implements VipConsDefineManager {
	private VipConsManagerDao vipConsManagerDao;
	
	

	/**
	 *  the vipConsManagerDao
	 * @return the vipConsManagerDao
	 */
	public VipConsManagerDao getVipConsManagerDao() throws DBAccessException {
		return vipConsManagerDao;
	}

	/**
	 *  the vipConsManagerDao to set
	 * @param vipConsManagerDao the vipConsManagerDao to set
	 */
	public void setVipConsManagerDao(VipConsManagerDao vipConsManagerDao) throws DBAccessException {
		this.vipConsManagerDao = vipConsManagerDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListByGroupNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByCommonGroupNo(
			String groupNo, long start, int limit) throws DBAccessException {
		if(null == groupNo || "".equals(groupNo))
				return null;
		return this.vipConsManagerDao.findVipConsDefineListByCommonGroupNo(groupNo, start, limit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListByCtrlGroupNo(java.lang.String, long, int)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByCtrlGroupNo(
			String groupNo, long start, int limit) throws DBAccessException {
		if(null == groupNo || "".equals(groupNo))
				return null;
		return this.vipConsManagerDao.findVipConsDefineListByCtrlGroupNo(groupNo, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListByLine(java.lang.String, com.nari.privilige.PSysUser, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByLine(String lineId,
			PSysUser pSysUser, long start, int limit) throws DBAccessException {
		if(null == lineId || "".equals(lineId)||null==pSysUser)
			return null;
		return this.vipConsManagerDao.findVipConsDefineListByLine(lineId, pSysUser, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListByOrgNo(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByOrgNo(String orgNo,
			String orgType, PSysUser pSysUser, long start, int limit) throws DBAccessException {
		if(null == orgType || "".equals(orgType)
				   ||null == orgNo || "".equals(orgNo)	
				    ||null==pSysUser){
		    		return null;
		    	}
		return this.vipConsManagerDao.findVipConsDefineListByOrgNo(orgNo, orgType, pSysUser, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListBySub(java.lang.String, com.nari.privilige.PSysUser, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListBySub(String subId,
			PSysUser pSysUser, long start, int limit) throws DBAccessException {
		if(null == subId || "".equals(subId)||null==pSysUser)
			 return null;
		return this.vipConsManagerDao.findVipConsDefineListBySub(subId, pSysUser, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsDefineManager#queryVipConsDefineListByTmnlAssetNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByTmnlAssetNo(
			String tmnlAssetNo, long start, int limit) throws DBAccessException {
		if(null==tmnlAssetNo||"".equals(tmnlAssetNo))
			return null;
		return this.vipConsManagerDao.findVipConsDefineListByTmnlAssetNo(tmnlAssetNo, start, limit);
	}

	@Override
	public Page<VipConsManagerDto> queryVipConsDefineListByConsNo(
			String consNo, long start, int limit) throws DBAccessException {
		if(null==consNo||"".equals(consNo))
			return null;
		return this.vipConsManagerDao.findVipConsDefineListByConsNo(consNo, start, limit);
	}

	@Override
	public Page<VipConsManagerDto> findVipConsListByStaffNo(String staffNo,
			long start, int limit) throws DBAccessException {
		if(null == staffNo || "".equals(staffNo))
			return null;
		return this.vipConsManagerDao.findVipConsListByStaffNo(staffNo, start, limit);
	}

}
