package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.EnergyCtrlManager;
import com.nari.baseapp.planpowerconsume.IEnergyControlJdbcDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWTmnlMonPctrlDao;
import com.nari.orderlypower.EnergyControlDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 月电量定值控业务层接口实现类
 * @author 姜炜超
 */
public class EnergyCtrlManagerImpl implements EnergyCtrlManager{
	private IWCtrlSchemeDao iWCtrlSchemeDao;//方案控制Dao处理对象
	private IEnergyControlJdbcDao iEnergyControlJdbcDao;//月电量定值控jdbc处理对象
	private IWTmnlMonPctrlDao iWTmnlMonPctrlDao; //月电量定值控明细表Dao处理对象
	
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}
	public void setiEnergyControlJdbcDao(IEnergyControlJdbcDao iEnergyControlJdbcDao) {
		this.iEnergyControlJdbcDao = iEnergyControlJdbcDao;
	}
	public void setiWTmnlMonPctrlDao(IWTmnlMonPctrlDao iWTmnlMonPctrlDao) {
		this.iWTmnlMonPctrlDao = iWTmnlMonPctrlDao;
	}

	/**
	 * 根据终端资产号查询月电量定值控Grid数据
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException{
		if(null == tmnlAssetNo || "".equals(tmnlAssetNo))
			return null;
		return this.iEnergyControlJdbcDao.findUserInfoByTmnlAssetNo(tmnlAssetNo);
	}
	
	/**
	 * 保存月电控方案明细
	 * @param schemeId
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public void saveSchemeDeal(Long schemeId, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException{
		try{
		    this.iEnergyControlJdbcDao.deleteBySchemeId(schemeId);
			for(int i=0;i<monPctrlList.size();i++){
				monPctrlList.get(i).setCtrlSchemeId(schemeId);
				this.iWTmnlMonPctrlDao.save(monPctrlList.get(i));
			}
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 新增方案，针对新增加方案的业务处理，主要是写入方案主表和月电量定值控表
	 * @param scheme
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean saveScheme(WCtrlScheme scheme, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException {
		if(null == scheme || null == monPctrlList || 0 == monPctrlList.size()){
			return false;
		}
		try {
			iWCtrlSchemeDao.save(scheme);
			//获取id后循环写入月电量定值控明细信息表
			for(int i = 0 ; i < monPctrlList.size(); i++){
				WTmnlMonPctrl monPctrl = (WTmnlMonPctrl)monPctrlList.get(i);
				monPctrl.setCtrlSchemeId(scheme.getCtrlSchemeId());
				this.iWTmnlMonPctrlDao.save(monPctrl);
			}
			return true;
		} catch (Exception e) {
			throw new DBAccessException();
		}
	}
	
	/**
	 * 修改方案，针对新增加方案的业务处理，主要是写入方案主表和月电量定值控表
	 * @param scheme
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme scheme, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException{
		if(null == scheme || null == monPctrlList || 0 == monPctrlList.size()){
			return false;
		}
		try{
		    this.iEnergyControlJdbcDao.deleteBySchemeId(scheme.getCtrlSchemeId());
		    this.iWCtrlSchemeDao.update(scheme);
			for(int i=0;i<monPctrlList.size();i++){
				monPctrlList.get(i).setCtrlSchemeId(scheme.getCtrlSchemeId());
				this.iWTmnlMonPctrlDao.save(monPctrlList.get(i));
			}
			return true;
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 根据供电单位编号查询月电量定值控Grid数据分页列表
	 * @param orgNo
	 * @param orgType
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByOrgNo(String orgNo, String orgType,PSysUser pSysUser)throws DBAccessException{
		if(null==orgNo||"".equals(orgNo)
			||null==orgType||"".equals(orgType)
			||null==pSysUser)
			return null;
		return this.iEnergyControlJdbcDao.findUserInfoByOrgNo(orgNo,orgType,pSysUser);
	}
	
	/**
	 * 根据客户编号查询月电量定值控Grid数据分页列表
	 * @param consNo
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByConsNo(String consNo)throws DBAccessException{
		if(null==consNo||"".equals(consNo))
			return  null;
		return this.iEnergyControlJdbcDao.findUserInfoByConsNo(consNo);
	}
	
	/**
	 * 根据线路Id查询月电量定值控Grid数据分页列表
	 * @param lineId
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByLineId(String lineId,PSysUser pSysUser)throws DBAccessException{
		if(null==lineId||"".equals(lineId)||null==pSysUser)
			return  null;
		return iEnergyControlJdbcDao.findUserInfoByLineId(lineId,pSysUser);
	}
	
	/**
	 * 根据组号查询月电量定值控Grid数据分页列表
	 * @param groupNo 
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByGroupNo(String nodeType,String groupNo)throws DBAccessException{		
		if(null==nodeType||"".equals(nodeType)
			||null==groupNo||"".equals(groupNo))
				return null;
		if("ugp".equals(nodeType)){
			return  this.iEnergyControlJdbcDao.findUserInfoByCommonGroupNo(groupNo);
		}else if("cgp".equals(nodeType)){
			return this.iEnergyControlJdbcDao.findUserInfoByContrlGroupNo(groupNo);
		}
		return null;
	}
	
	/**
	 * 根据变电站标识查询月电量定值控Grid数据分页列表
	 * @param subsId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridBySubsId(String subsId,PSysUser pSysUser)throws DBAccessException{
		if(null==subsId||"".equals(subsId)||null==pSysUser)
			return null;		
		return this.iEnergyControlJdbcDao.findUserInfoBySubsId(subsId,pSysUser);
	}
	
	/**
	 * 根据方案号查询月电量定值控Grid数据分页列表
	 * @param schemeNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridBySchemeId(Long schemeId)throws DBAccessException{
		if(null==schemeId)
			return null;
		return this.iEnergyControlJdbcDao.findUserInfoBySchemeId(schemeId);
	}
	
/*	*//**
	 * 更新月电量定值控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateMonPctrlStatus(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException{
		WTmnlMonPctrl monPctrl = null;
		if(null != monPctrlList && 0 < monPctrlList.size()){
			for(int i = 0 ; i < monPctrlList.size(); i++){
				monPctrl = (WTmnlMonPctrl)monPctrlList.get(i);
			    if(null != monPctrl && null != monPctrl.getTmnlAssetNo()){
			    	this.iEnergyControlJdbcDao.updateMonPctrlStatus(monPctrl);
			    }
			}
		}
	}*/
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void updateMonPctrl(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException{
		WTmnlMonPctrl monPctrl = null;
		if(null != monPctrlList && 0 <monPctrlList.size()){
			for(int i = 0 ; i < monPctrlList.size(); i++){
				monPctrl = (WTmnlMonPctrl)monPctrlList.get(i);
			    if(null != monPctrl && null != monPctrl.getTmnlAssetNo()){
			    	this.iEnergyControlJdbcDao.updateMonPctrl(monPctrl);
			    }
			}
		}
	}*/
	
/*	*//**
	 * 查询月电量定值控方案，用于校验状态编码是否可以进行投入和解除操作
	 * @param filters
	 * @return List<WTmnlMonPctrl> 
	 * @throws DBAccessException 数据库异常
	 *//*
	public List<WTmnlMonPctrl> queryMonPctrlList(List<PropertyFilter> filters)throws DBAccessException{
		if(null != filters && 0 < filters.size()){
			return iWTmnlMonPctrlDao.findBy(filters);
		}
		return null;
	}*/
	
/*	*//**
	 * 新增或修改月电量定值控方案，针对参数下发前用户的操作，只修改月电量定值控明细表
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void saveOrUpdateMonPctrl(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException{
		//获取id后循环写入报停控明细信息表
		if(null != monPctrlList && 0 < monPctrlList.size()){
			for(int i = 0 ; i < monPctrlList.size(); i++){
				WTmnlMonPctrl monPctrl = (WTmnlMonPctrl)monPctrlList.get(i);
				try {
					iEnergyControlJdbcDao.saveOrUpdateByParam(monPctrl);
				} catch (Exception e) {
					throw new DBAccessException();
				}
			}
		}
	}*/
	
/*	*//**
	 * 新增或修改月电量定值控方案，针对参数下发前用户的操作，只修改月电量定值控明细表
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 *//*
	public void saveOrUpdateMonPctrl(WTmnlMonPctrl monPctrl) throws DBAccessException{
		try {
			this.iWTmnlMonPctrlDao.saveOrUpdate(monPctrl);
		} catch (Exception e) {
			throw new DBAccessException();
		}
	}*/

	
    
    /**
	 * 根据方案Id查询某种类型方案
	 * @param schemeId
	  * @param schemeType 
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> querySchemesById(Long schemeId)throws DBAccessException{
		if(null==schemeId)
			return null;
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter s = new PropertyFilter("ctrlSchemeId", schemeId);
			filters.add(s);
		    return this.iWCtrlSchemeDao.findBy(filters);
		}
		catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
	/**
	 * 更新月电量定值控方案明细
	 */
	public void updateSchemeDetail(WTmnlMonPctrl wTmnlMonPctrl,short status) throws DBAccessException{
		if(null==wTmnlMonPctrl)
			return;
		try {
			this.iEnergyControlJdbcDao.updateMonPowerContrl(wTmnlMonPctrl, status);
		} catch (Exception e) {
			throw new DBAccessException();
		}
		
	}
}
