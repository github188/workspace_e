package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWTmnlRejectDao;
import com.nari.baseapp.planpowerconsume.IWTmnlRejectJdbcDao;
import com.nari.baseapp.planpowerconsume.IWTmnlRejectManager;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlReject;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 
 * 创建时间：2009-12-23 下午05:37:46 
 * 描述：
 */

public class WTmnlRejectManagerImpl implements IWTmnlRejectManager{

	private IWTmnlRejectDao iWTmnlRejectDao;
	private IWCtrlSchemeDao iWCtrlSchemeDao;
    private IWTmnlRejectJdbcDao iWTmnlRejectJdbcDao;
    
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}

	public void setiWTmnlRejectJdbcDao(IWTmnlRejectJdbcDao iWTmnlRejectJdbcDao) {
		this.iWTmnlRejectJdbcDao = iWTmnlRejectJdbcDao;
	}

	public IWTmnlRejectDao getiWTmnlRejectDao() {
		return iWTmnlRejectDao;
	}

	public void setiWTmnlRejectDao(IWTmnlRejectDao iWTmnlRejectDao) {
		this.iWTmnlRejectDao = iWTmnlRejectDao;
	}
	
	 /**
     * 添加终端剔除信息
     * @param wTmnlReject
     * @throws DBAccessException
     */
	public void saveTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException{
		try{
			  this.iWTmnlRejectDao.save(wTmnlReject);
			}catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			}
	}
	
	/**
	  * 修改终端剔除信息
	  * @param wTmnlReject
	  * @throws DBAccessException
	  */
    public void updateTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException{
		try {
			  this.iWTmnlRejectDao.update(wTmnlReject);
			} catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			}	
    	
    }
    
    /**
	  * 添加修改终端剔除信息
	  * @param wTmnlReject
	  * @throws DBAccessException
	  */
   public void saveOrUpdateTmnlRej(WTmnlReject wTmnlReject)throws DBAccessException{
		try {
			  this.iWTmnlRejectDao.saveOrUpdate(wTmnlReject);
			} catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			}	
   }
    
    /**
	  * 根据条件查询终端剔除信息
	  * @param filters
	  * @return
	  * @throws DBAccessException
	  */
    public List<WTmnlReject>findTmnlRejBy(List<PropertyFilter> filters)throws DBAccessException{
    	try {
			return this.iWTmnlRejectDao.findBy(filters);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
    	
    }
    
    /**
	 * 另存为方案
	 * @param wCtrlScheme 方案
	 * @param rejectList  方案明细
	 * @throws DBAccessException
	 */
	 public Boolean saveScheme(WCtrlScheme wCtrlScheme,List<WTmnlReject> rejectList)throws DBAccessException{
		 if(null == wCtrlScheme || null == rejectList || 0 == rejectList.size()){
				return false;
		 }
		 try {
			 this.iWCtrlSchemeDao.save(wCtrlScheme);
			 //获取id后循环写入终端保电表
			 for(int i = 0 ; i < rejectList.size(); i++){
			    WTmnlReject wt = (WTmnlReject)rejectList.get(i);
				wt.setCtrlSchemeId(wCtrlScheme.getCtrlSchemeId());
				this.iWTmnlRejectDao.save(wt);
			 }
			 return true;
		 }catch(DataAccessException e) {
				e.printStackTrace();
				throw new DBAccessException(BaseException.processDBException(e));
		 }
	 }                                                                                                                                                                                              
		                                                                                                                                                                                                        
		/**
		 * 根据终端资产号号查询终端信息
		 * @param tmnlAssetNo
		 * @return
		 * @throws DBAccessException
		 */
		public List<WTmnlReject> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
			if(null == tmnlAssetNo || "".equals(tmnlAssetNo)){
	    		return null;
	    	}
			return this.iWTmnlRejectJdbcDao.findTmnlByTmnlAssetNo(tmnlAssetNo);
		}
		/**
		 * 根据供电单位编号查询终端信息
		 * @param orgNo 供电单位编号
		 * @throws DBAccessException
		 */
		public List<WTmnlReject> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser)throws DBAccessException{
			if(null == orgNo || "".equals(orgNo)
			||null == orgType || "".equals(orgType)
			||null== pSysUser){
	    		return null;
	    	}
			return this.iWTmnlRejectJdbcDao.findTmnlByOrgNo(orgNo,orgType,pSysUser);
		}
		
		/**
	     * 根据线路Id查询终端信息
	     * @param lineId 线路Id
	     * @return 
	     * @throws DBAccessException
	     */
	    public List<WTmnlReject> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
	    	if(null == lineId || "".equals(lineId)||null==pSysUser)
	    		return null;
			return this.iWTmnlRejectJdbcDao.findTmnlByLineId(lineId,pSysUser);
	    }
	    
	    /**
	     * 根据群组编号查询终端信息
	     * @param nodeType 左边树节点类型
	     * @param groupNo  群组编号
	     * @return 
	     * @throws DBAccessException
	     */
	    public List<WTmnlReject> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException{
	    	if(null == nodeType || "".equals(nodeType)||null == groupNo || "".equals(groupNo)){
	    		return null;
	    	}
	    	if("ugp".equals(nodeType))
	    	    return this.iWTmnlRejectJdbcDao.findTmnlByCommonGroupNo(groupNo);
	    	else if("cgp".equals(nodeType))
	    		return this.iWTmnlRejectJdbcDao.findTmnlByCtrlGroupNo(groupNo);
			return null;
	    }
	    /**
	     * 根据变电站标识查询终端信息
	     * @param subsId 变电站标识
	     * @return 
	     * @throws DBAccessException
	     */
	    public List<WTmnlReject> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException{
	    	if(null == subsId || "".equals(subsId)||null==pSysUser)
	    		return null;
			return this.iWTmnlRejectJdbcDao.findTmnlBySubsId(subsId,pSysUser);
	    }
	    
	    /**
	     * 根据方案ID查询终端信息
	     * @param schemeId  方案ID
	     * @return
	     * @throws DBAccessException
	     */
	    public List<WTmnlReject> queryTmnlBySchemeId(Long schemeId) throws DBAccessException{
	       if(null == schemeId)
	    		return null;
	       try{
	    	   return this.iWTmnlRejectJdbcDao.findTmnlBySchemeId(schemeId);
		    }
			catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			}	
	    }
	    
	    /**
	 	 * 更新剔除标识
	 	 * @throws DBAccessException
	 	 */
	 	public void  updateEliminateFlag(String tmnlAssetNo,Integer flag)throws DBAccessException{
	 		if(null==tmnlAssetNo||"".equals(tmnlAssetNo)||null==flag)
				return;
	 		this.iWTmnlRejectJdbcDao.updateEliminate(tmnlAssetNo, flag);
	 	}
	 	
	 	/**
		 * 修改方案，针对保存方案的业务处理，写入方案主表和终端剔除表
		 * @param wCtrlScheme
		 * @param rejectList
		 * @return 
		 * @throws DBAccessException 数据库异常
		 */
		public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlReject> rejectList)throws DBAccessException{
			if(null == wCtrlScheme ||null==wCtrlScheme.getCtrlSchemeId()|| null == rejectList || 0 == rejectList.size())
				return false;
			try {
				this.iWTmnlRejectJdbcDao.deleteBySchemeId(wCtrlScheme.getCtrlSchemeId());
				this.iWCtrlSchemeDao.update(wCtrlScheme);
				for(int i=0;i<rejectList.size();i++){
					this.iWTmnlRejectDao.save(rejectList.get(i));
				}
				return true;
			}catch(DataAccessException e) {
				e.printStackTrace();
				throw new DBAccessException(BaseException.processDBException(e));
			}
		}
		
}
