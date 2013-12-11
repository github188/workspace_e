package com.nari.advapp.vipconsman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.vipconsman.VipConsManager;
import com.nari.advapp.vipconsman.VipConsUserDao;
import com.nari.advapp.vipconsman.VipConsUserManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 重点用户新增service实现类
 * @author ChunMingLi
 * @since  2010-6-5
 *
 */
public class VipConsUserManagerImpl implements VipConsUserManager {
	private VipConsUserDao vipConsUserDao;

	/**
	 *  the vipConsUserDao to set
	 * @param vipConsUserDao the vipConsUserDao to set
	 */
	public void setVipConsUserDao(VipConsUserDao vipConsUserDao) {
		this.vipConsUserDao = vipConsUserDao;
	} 
	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsUserManager#saveVipUser(java.util.List)
	 */
	public int saveVipUser(List<VipConsManager> vipConsUserList)
			throws DBAccessException {
		try {
			if (vipConsUserList == null || vipConsUserList.size() < 1) {
				return 0;
			}
			for (VipConsManager vipUser : vipConsUserList) {
				vipConsUserDao.saveOrUpdate(vipUser);
			}
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		}
		return 1;
	}
	/*
	 * (non-Javadoc)
	 * @see com.nari.advapp.vipconsman.VipConsUserManager#deleteVipUser(java.util.List)
	 */
	@Override
	public int deleteVipUser(List<VipConsManager> delUserList) throws DBAccessException {
		
		try {
			if (delUserList == null || delUserList.size() < 1) {
				return 0;
			}
			for (VipConsManager vipUser : delUserList) {
				vipConsUserDao.delete(vipUser);
			}
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		}
		return 1;
	}
	
}
