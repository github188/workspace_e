package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.PowerCtrlQueryDao;
import com.nari.baseapp.planpowerconsume.PowerCtrlQueryManager;
import com.nari.orderlypower.PwrctrlSchemeExecBean;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class PowerCtrlQueryManagerImpl implements PowerCtrlQueryManager {
	
	private PowerCtrlQueryDao powerCtrlQueryDao;
	
	public void setPowerCtrlQueryDao(PowerCtrlQueryDao powerCtrlQueryDao) {
		this.powerCtrlQueryDao = powerCtrlQueryDao;
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecBySchemeId(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByOrgNo(
			String orgNo,String orgType, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecByOrgNo(orgNo, orgType, user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public String findSchemeValidSectionNo(Long schemeId) throws DBAccessException {
		try {
			List<String> validSectionNoList = powerCtrlQueryDao.findSchemeValidSectionNo(schemeId);
			String validSectionNo ="";
			if(validSectionNoList == null || validSectionNoList.size() ==0){
				return validSectionNo;
			}
			for (int i = 0; i < validSectionNoList.size()-1; i++) {
				validSectionNo += (validSectionNoList.get(i) + ",");
			}
			validSectionNo += validSectionNoList.get(validSectionNoList.size()-1);
			return validSectionNo;
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public String findSchemeTemplateId(Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findSchemeTemplateId(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public String findSchemeTemplateName(Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findSchemeTemplateName(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public String findCxkSchemeTemplateName(Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findCxkSchemeTemplateName(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public String findSchemeCurveNo(Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findSchemeCurveNo(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public WFloatDownCtrl findfloatDownCtrl(Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findfloatDownCtrl(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException{
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByOrgNo(
			String orgNo,String orgType, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecByOrgNo(orgNo, orgType, user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecBySchemeId(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByOrgNo(String orgNo, String orgType, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecByOrgNo(orgNo, orgType, user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecBySchemeId(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecByCgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByLineId(
			Long lineId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecByLineId(lineId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySubsId(
			Long subsId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecBySubsId(subsId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFactctrlSchemeExecByUgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecByCgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecByLineId(lineId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySubsId(Long subsId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecBySubsId(subsId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findFloatSchemeExecByUgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecByCgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecByLineId(lineId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySubsId(
			Long subsId, PSysUser user) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecBySubsId(subsId,user);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		try {
			return powerCtrlQueryDao.findPwrctrlSchemeExecByUgroupNo(groupNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Boolean queryCtrlSchemeName(String ctrlSchemeName, String staffNo)
			throws DBAccessException {
		try {
			return powerCtrlQueryDao.queryCtrlSchemeName(ctrlSchemeName, staffNo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Integer findSchemeSecLength(Long schemeId)   throws DBAccessException{
		try {
			return powerCtrlQueryDao.findSchemeSecLength(schemeId);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}	
}
