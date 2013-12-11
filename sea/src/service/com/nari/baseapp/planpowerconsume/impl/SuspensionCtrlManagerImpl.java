package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.ISuspensionControlJdbcDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWTmnlBusinessDao;
import com.nari.baseapp.planpowerconsume.SuspensionCtrlManager;
import com.nari.basicdata.VwLimitType;
import com.nari.basicdata.VwLimitTypeJdbcDao;
import com.nari.orderlypower.SuspensionControlDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 营业报停控业务层接口实现类
 * @author 姜炜超
 */
public class SuspensionCtrlManagerImpl implements SuspensionCtrlManager{

	private IWCtrlSchemeDao iWCtrlSchemeDao;//方案控制Dao处理对象
	private ISuspensionControlJdbcDao iSuspensionControlJdbcDao;//营业报停控jdbc处理对象
	private VwLimitTypeJdbcDao vwLimitTypeJdbcDao;//限电类型Dao处理对象
	private IWTmnlBusinessDao iWTmnlBusinessDao; //营业报停控明细表Dao处理对象
	
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}

	public void setVwLimitTypeJdbcDao(VwLimitTypeJdbcDao vwLimitTypeJdbcDao) {
		this.vwLimitTypeJdbcDao = vwLimitTypeJdbcDao;
	}
	
	public void setiSuspensionControlJdbcDao(
			ISuspensionControlJdbcDao iSuspensionControlJdbcDao) {
		this.iSuspensionControlJdbcDao = iSuspensionControlJdbcDao;
	}

	public void setiWTmnlBusinessDao(IWTmnlBusinessDao iWTmnlBusinessDao) {
		this.iWTmnlBusinessDao = iWTmnlBusinessDao;
	}

	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，写入方案主表和营业报停控表
	 * @param scheme
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean saveScheme(WCtrlScheme scheme, List<WTmnlBusiness> busiList) throws DBAccessException {
		if(null == scheme || null == busiList || 0 == busiList.size()){
			return false;
		}
		this.iWCtrlSchemeDao.save(scheme);
		
		//获取id后循环写入报停控明细信息表
		for(int i = 0 ; i < busiList.size(); i++){
			WTmnlBusiness busi = (WTmnlBusiness)busiList.get(i);
			busi.setCtrlSchemeId(scheme.getCtrlSchemeId());
			try {
				this.iWTmnlBusinessDao.save(busi);
			} catch (Exception e) {
				throw new DBAccessException();
			}
		}
		return true;
	}

	/**
	 * 查询限电类型列表
	 * @return 限电类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwLimitType> queryLimitTypeList() throws DBAccessException {
	    return	vwLimitTypeJdbcDao.findAll();
	}	
	
	/**
	 * 根据终端资产编号查询营业报停控Grid数据分页列表
	 * @param tmnlAssetNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException{
		if(null==tmnlAssetNo||"".equals(tmnlAssetNo))
			 return null;
		return this.iSuspensionControlJdbcDao.findUserInfoByTmnlAssetNo(tmnlAssetNo);
		
	}
	
	
	/**
	 * 根据供电单位编号查询营业报停控Grid数据分页列表
	 * @param orgNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByOrgNo(String orgNo, String orgType,PSysUser pSysUser)throws DBAccessException{
		if(null==orgNo||"".equals(orgNo)
			||null==orgType||"".equals(orgType)
			||null==pSysUser)
			 return null;
		return this.iSuspensionControlJdbcDao.findUserInfoByOrgNo(orgNo,orgType,pSysUser);
		
	}
	
	/**
	 * 根据客户编号查询营业报停控Grid数据分页列表
	 * @param consNo
	 * @return Page<SuspensionControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException{
		if(null==tmnlAssetNo||"".equals(tmnlAssetNo))
			return null;
		return  this.iSuspensionControlJdbcDao.findUserInfoByTmnlAssetNo(tmnlAssetNo);
	}
	
	/**
	 * 根据线路Id查询营业报停控Grid数据分页列表
	 * @param lineId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByLineId(String lineId,PSysUser pSysUser)throws DBAccessException{
		if(null==lineId||"".equals(lineId)||null==pSysUser)
			return null;
		return this.iSuspensionControlJdbcDao.findUserInfoByLineId(lineId,pSysUser);
	}
	
	/**
	 * 根据组号查询营业报停控Grid数据分页列表
	 * @param groupNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByGroupNo(String nodeType,String groupNo)throws DBAccessException{
		if(null == groupNo || "".equals(groupNo)
			||null == nodeType || "".equals(nodeType)){
    		return null;
    	}
		if("ugp".equals(nodeType))
			 return  this.iSuspensionControlJdbcDao.findUserInfoByCommonGroupNo(groupNo);
		else if("cgp".equals(nodeType))
			 return  this.iSuspensionControlJdbcDao.findUserInfoByContrlGroupNo(groupNo);
		else
			 return null;
	}
	
	/**
	 * 根据变电站标识查询营业报停控Grid数据分页列表
	 * @param subsId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridBySubsId(String subsId,PSysUser pSysUser)throws DBAccessException{
		if(null == subsId || "".equals(subsId)||null == pSysUser)
	    		return null;
		return this.iSuspensionControlJdbcDao.findUserInfoBySubsId(subsId,pSysUser);
	}
	
	/**
	 * 根据方案号查询营业报停控Grid数据分页列表
	 * @param schemeNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridBySchemeId(Long schemeId)throws DBAccessException{
		if(null==schemeId)
			return null;	
		return  this.iSuspensionControlJdbcDao.findUserInfoBySchemeId(schemeId);
	}
		
	/**
	 * 更新营业报停控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateBusiStatus(List<WTmnlBusiness> busiList) throws DBAccessException{
		WTmnlBusiness busi = null;
		if(null != busiList && 0 < busiList.size()){
			for(int i = 0 ; i < busiList.size(); i++){
			    busi = (WTmnlBusiness)busiList.get(i);
			    if(null != busi && null != busi.getTmnlAssetNo()){
			    	this.iSuspensionControlJdbcDao.updateBusiStatus(busi);
			    }
			}
		}
	}*/
	
	/**
	 * 更新营业报停控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateBusiness(List<WTmnlBusiness> busiList) throws DBAccessException{
		WTmnlBusiness busi = null;
		if(null != busiList && 0 < busiList.size()){
			for(int i = 0 ; i < busiList.size(); i++){
			    busi = (WTmnlBusiness)busiList.get(i);
			    if(null != busi && null != busi.getTmnlAssetNo()){
			    	this.iSuspensionControlJdbcDao.updateBusiness(busi);
			    }
			}
		}
	}*/
	
	 /**
	 * 修改方案，针对保存方案的业务处理，写入方案主表和营业报停控表
	 * @param wCtrlScheme
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlBusiness> busiList)throws DBAccessException{
		if(null == wCtrlScheme ||null==wCtrlScheme.getCtrlSchemeId()|| null == busiList || 0 == busiList.size())
			return false;
		try{
			this.iSuspensionControlJdbcDao.deleteBySchemeId(wCtrlScheme.getCtrlSchemeId());
			this.iWCtrlSchemeDao.update(wCtrlScheme);
			for(int i=0 ;i<busiList.size();i++){
				 this.iWTmnlBusinessDao.save(busiList.get(i));
			}
			return true;
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	
	/**
	 * 查询营业报停控方案，用于校验状态编码是否可以进行投入和解除操作
	 * @param filters
	 * @return List<WTmnlBusiness> 
	 * @throws DBAccessException 数据库异常
	 *//*
	public List<WTmnlBusiness> queryBusiList(List<PropertyFilter> filters)throws DBAccessException{
		if(null != filters && 0 < filters.size()){
			return iWTmnlBusinessDao.findBy(filters);
		}
		return null;
	}*/
	
	/**
	 * 新增或修改营业报停控方案，针对参数下发前用户的操作
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void saveOrUpdateBusiness(WTmnlBusiness busi) throws DBAccessException{
		if(null == busi)
			return;
		try{
			this.iWTmnlBusinessDao.saveOrUpdate(busi);
		}catch (Exception e) {
			throw new DBAccessException();
		}
	}*/
}
