package com.nari.baseapp.planpowerconsume.impl;


import java.util.List;

import com.nari.baseapp.planpowerconsume.IWTmnlJdbcDao;
import com.nari.baseapp.planpowerconsume.IWTmnlManager;
import com.nari.orderlypower.WTmnlDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-28 下午03:40:38 
 * 描述：从左边树获取终端信息接口实现类
 */

public class WTmnlManagerImpl implements IWTmnlManager {
	private IWTmnlJdbcDao iWTmnlJdbcDao;

	public void setiWTmnlJdbcDao(IWTmnlJdbcDao iWTmnlJdbcDao) {
		this.iWTmnlJdbcDao = iWTmnlJdbcDao;
	}
		
	/**
	 * 根据用户编号查询终端信息
	 * @param tmnlAssetNo 终端资产号
	 * @param start
	 * @param limit
	 * @return Page<WTmnlDto>
	 */
	public Page<WTmnlDto> queryTmnlByConsNo(String consNo,int  start, int limit)throws DBAccessException{
		if(null == consNo || "".equals(consNo)){
    		return null;
    	}
		Page<WTmnlDto> page = new Page<WTmnlDto>();
		page=this.iWTmnlJdbcDao.findTmnlByConsNo(consNo,start,limit);
		return page;
	}
	

	/**
	 * 根据终端资产号号查询终端信息
	 * @param tmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> queryTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		if(null == tmnlAssetNo || "".equals(tmnlAssetNo)){
    		return null;
    	}
		return this.iWTmnlJdbcDao.findTmnlByTmnlAssetNo(tmnlAssetNo);
	}
	/**
	 * 根据供电单位编号查询终端信息
	 * @param orgNo 供电单位编号
	 * @throws DBAccessException
	 */
	public List<WTmnlDto> queryTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser)throws DBAccessException{
		if(null == orgNo || "".equals(orgNo)
		||null == orgType || "".equals(orgType)
		||null== pSysUser){
    		return null;
    	}
		return this.iWTmnlJdbcDao.findTmnlByOrgNo(orgNo,orgType,pSysUser);
	}
	
	/**
     * 根据线路Id查询终端信息
     * @param lineId 线路Id
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlDto> queryTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
    	if(null == lineId || "".equals(lineId)||null==pSysUser)
    		return null;
		return this.iWTmnlJdbcDao.findTmnlByLineId(lineId,pSysUser);
    }
    
    /**
     * 根据群组编号查询终端信息
     * @param nodeType 左边树节点类型
     * @param groupNo  群组编号
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlDto> queryTmnlByGroupNo(String nodeType,String groupNo) throws DBAccessException{
    	if(null == nodeType || "".equals(nodeType)||null == groupNo || "".equals(groupNo)){
    		return null;
    	}
    	if("ugp".equals(nodeType))
    	    return this.iWTmnlJdbcDao.findTmnlByCommonGroupNo(groupNo);
    	else if("cgp".equals(nodeType))
    		return this.iWTmnlJdbcDao.findTmnlByCtrlGroupNo(groupNo);
		return null;
    }
    /**
     * 根据变电站标识查询终端信息
     * @param subsId 变电站标识
     * @return 
     * @throws DBAccessException
     */
    public List<WTmnlDto> queryTmnlBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException{
    	if(null == subsId || "".equals(subsId)||null==pSysUser)
    		return null;
		return this.iWTmnlJdbcDao.findTmnlBySubsId(subsId,pSysUser);
    }
    
//    /**
//     * 根据方案ID查询终端信息
//     * @param schemeId  方案ID
//     * @return
//     * @throws DBAccessException
//     */
//    public List<WTmnlDto> queryTmnlBySchemeId(String schemeType,Long schemeId) throws DBAccessException{
//    	if(null == schemeType || "".equals(schemeType)||null == schemeId)
//    		return null;
//    	if("protect".equals(schemeType))
//    	    return this.iWTmnlJdbcDao.findProtectTmnlBySchemeId(schemeId);
//    	else if("eliminate".equals(schemeType))
//    		return this.iWTmnlJdbcDao.findEliminateTmnlBySchemeId(schemeId);
//    	return null;
//    }
//	/**
//	 * 查询相关的信息放入备选用户的Grid里
//	 * @param consNo 用户编号
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> queryWTmnlBy(String consNo,String orgNo,Long schemeId,String nodeType,long start, int limit) throws DBAccessException{
//		Page<WTmnlDto> page = new Page<WTmnlDto>();
//		if("usr".equals(nodeType)){
//			page=this.iWTmnlJdbcDao.findWTmnlByCons(consNo,start,limit);
//		}else if("org".equals(nodeType)){
//			page=this.iWTmnlJdbcDao.findWTmnlByOrg(orgNo,start,limit);
//		}else if("ctrlScheme".equals(nodeType))
//			page=this.iWTmnlJdbcDao.findWTmnlByScheme(schemeId,start,limit);
//		return page;
//	}
	
//	/**
//	 * 查询相关信息放入备选用户Grid中（带总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> queryWTmnlByWithT(String consNo,String orgNo,Long schemeId,String nodeType,long start, int limit) throws DBAccessException{
//		Page<WTmnlDto> page = new Page<WTmnlDto>();
//		if("usr".equals(nodeType)){
//			page=this.iWTmnlJdbcDao.findWTmnlByCons(consNo,start,limit);
//		}else if("org".equals(nodeType)){
//			page=this.iWTmnlJdbcDao.findWTmnlByOrg(orgNo,start,limit);
//		}else if("ctrlScheme".equals(nodeType))
//			page=this.iWTmnlJdbcDao.findWTmnlBySchemeWithT(schemeId,start,limit);
//		return page;
//	}

}
