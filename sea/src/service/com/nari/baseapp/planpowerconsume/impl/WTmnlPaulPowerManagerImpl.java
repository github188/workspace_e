package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.IRTmnlRunJdbcDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWTmnlPaulPowerDao;
import com.nari.baseapp.planpowerconsume.IWTmnlPaulPowerJdbcDao;
import com.nari.baseapp.planpowerconsume.IWTmnlPaulPowerManager;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-15 上午10:07:02 
 * 描述：
 */

public class WTmnlPaulPowerManagerImpl  implements IWTmnlPaulPowerManager{
	
	private IWTmnlPaulPowerDao iWTmnlPaulPowerDao;
	private IWTmnlPaulPowerJdbcDao iWTmnlPaulPowerJdbcDao;
    private IWCtrlSchemeDao  iWCtrlSchemeDao;
	private IRTmnlRunJdbcDao iRTmnlRunJdbcDao;
    
	public IWTmnlPaulPowerDao getiWTmnlPaulPowerDao() {
		return iWTmnlPaulPowerDao;
	}
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}

	public void setiWTmnlPaulPowerDao(IWTmnlPaulPowerDao iWTmnlPaulPowerDao) {
		this.iWTmnlPaulPowerDao = iWTmnlPaulPowerDao;
	}
	
	public void setiWTmnlPaulPowerJdbcDao(
			IWTmnlPaulPowerJdbcDao iWTmnlPaulPowerJdbcDao) {
		this.iWTmnlPaulPowerJdbcDao = iWTmnlPaulPowerJdbcDao;
	}

	public void setiRTmnlRunJdbcDao(IRTmnlRunJdbcDao iRTmnlRunJdbcDao) {
		this.iRTmnlRunJdbcDao = iRTmnlRunJdbcDao;
	}
	/**
	 * 新增终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void savePaulPower(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException{
		try {
			this.iWTmnlPaulPowerDao.save(wTmnlPaulPower);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 修改终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void updatePaulPower(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException{
		try {
			this.iWTmnlPaulPowerDao.update(wTmnlPaulPower);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
		
	}
	
	/**
	 * 新增或修改终端保电信息
	 * @param wTmnlPaulPower
	 * @throws DBAccessException
	 */
	public void saveOrUpdatePaulPower(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException {
		if(null==wTmnlPaulPower)
			return;
		try{
		  this.iWTmnlPaulPowerJdbcDao.saveOrUpdateByParam(wTmnlPaulPower);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	public List<WTmnlPaulPower> findPaulPowerBy(List<PropertyFilter> filters)
			throws DBAccessException {
		try {
			return this.iWTmnlPaulPowerDao.findBy(filters);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 新增方案，针对新增加方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean saveScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException{
		if(null == wCtrlScheme || null == PaulPowerList || 0 == PaulPowerList.size()){
			return false;
		}
		try {
			this.iWCtrlSchemeDao.save(wCtrlScheme);
			//获取id后循环写入终端保电表
			for(int i = 0 ; i < PaulPowerList.size(); i++){
				WTmnlPaulPower wt = (WTmnlPaulPower)PaulPowerList.get(i);
				wt.setCtrlSchemeId(wCtrlScheme.getCtrlSchemeId());
				this.iWTmnlPaulPowerDao.save(wt);
			}
			return true;
	    }catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
	    }
	}

	/**
	 * 修改方案，针对保存方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException{
		if(null == wCtrlScheme ||null==wCtrlScheme.getCtrlSchemeId()|| null == PaulPowerList || 0 == PaulPowerList.size()){
			return false;
		}
		try {
			this.iWTmnlPaulPowerJdbcDao.deleteBySchemeId(wCtrlScheme.getCtrlSchemeId());
			this.iWCtrlSchemeDao.update(wCtrlScheme);
			for(int i=0;i<PaulPowerList.size();i++){
				this.iWTmnlPaulPowerDao.save(PaulPowerList.get(i));
			}
			return true;
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，写入方案主表和终端保电表
	 * @param wCtrlScheme
	 * @param PaulPowerList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdateScheme(WCtrlScheme wCtrlScheme,List<WTmnlPaulPower> PaulPowerList)throws DBAccessException{
		if(null == wCtrlScheme || null == PaulPowerList || 0 == PaulPowerList.size()){
			return;
		}
		this.iWCtrlSchemeDao.save(wCtrlScheme);
		//获取id后循环写入终端保电表
	
		for(int i = 0 ; i < PaulPowerList.size(); i++){
			WTmnlPaulPower wt = (WTmnlPaulPower)PaulPowerList.get(i);
			wt.setCtrlSchemeId(wCtrlScheme.getCtrlSchemeId());
			try {
				this.iWTmnlPaulPowerJdbcDao.saveOrUpdateByScheme(wt);
			}catch(DataAccessException e) {
				e.printStackTrace();
				throw new DBAccessException(BaseException.processDBException(e));
			}
	   }	
	}
	
	
	/**
	 * 更新保电标识
	 * @throws DBAccessException
	 */
	public void  updateProtectFlag(String tmnlAssetNo,Integer flag)throws DBAccessException{
		if(null==flag||null==tmnlAssetNo||"".equals(tmnlAssetNo))
			return;
		this.iRTmnlRunJdbcDao.updateProtect(tmnlAssetNo,flag);
	}
	/**
	 * 根据终端资产号号查询终端信息
	 * @param tmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		if(null == tmnlAssetNo || "".equals(tmnlAssetNo)){
    		return null;
    	}
		return this.iWTmnlPaulPowerJdbcDao.findTmnlByTmnlAssetNo(tmnlAssetNo);
	}
	/**
	 * 根据供电单位编号查询终端信息
	 * @param orgNo 供电单位编号
	 * @throws DBAccessException
	 */
	public List<WTmnlPaulPower> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser)throws DBAccessException{
		if(null == orgNo || "".equals(orgNo)
		||null == orgType || "".equals(orgType)
		||null== pSysUser){
    		return null;
    	}
		return this.iWTmnlPaulPowerJdbcDao.findTmnlByOrgNo(orgNo,orgType,pSysUser);
	}
	
	/**
     * 根据线路Id查询终端信息
     * @param lineId 线路Id
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlPaulPower> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
    	if(null == lineId || "".equals(lineId)||null==pSysUser)
    		return null;
		return this.iWTmnlPaulPowerJdbcDao.findTmnlByLineId(lineId,pSysUser);
    }
    
    /**
     * 根据群组编号查询终端信息
     * @param nodeType 左边树节点类型
     * @param groupNo  群组编号
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlPaulPower> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException{
    	if(null == nodeType || "".equals(nodeType)||null == groupNo || "".equals(groupNo)){
    		return null;
    	}
    	if("ugp".equals(nodeType))
    	    return this.iWTmnlPaulPowerJdbcDao.findTmnlByCommonGroupNo(groupNo);
    	else if("cgp".equals(nodeType))
    		return this.iWTmnlPaulPowerJdbcDao.findTmnlByCtrlGroupNo(groupNo);
		return null;
    }
    /**
     * 根据变电站标识查询终端信息
     * @param subsId 变电站标识
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlPaulPower> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException{
    	if(null == subsId || "".equals(subsId)||null==pSysUser)
    		return null;
		return this.iWTmnlPaulPowerJdbcDao.findTmnlBySubsId(subsId,pSysUser);
    }
    
    /**
     * 根据方案ID查询终端信息
     * @param schemeId  方案ID
     * @return
     * @throws DBAccessException
     */
    public List<WTmnlPaulPower> queryTmnlBySchemeId(Long schemeId) throws DBAccessException{
    	if(null == schemeId)
    		return null;
    	return this.iWTmnlPaulPowerJdbcDao.findProtectTmnlBySchemeId(schemeId);	
    }


}
