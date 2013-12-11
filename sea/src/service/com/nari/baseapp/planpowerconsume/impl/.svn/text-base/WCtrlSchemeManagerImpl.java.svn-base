package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeManager;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 终端方案接口实现类
 * @author 姜海辉
 *
 */
public class WCtrlSchemeManagerImpl implements IWCtrlSchemeManager{
	
	private IWCtrlSchemeDao iWCtrlSchemeDao;//方案控制Dao处理对象
	
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}

	/**
	 * 根据方案名称查询方案
	 * @param staffNo
	 * @param schemeName
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> querySchemesByName(String staffNo,String schemeName)throws DBAccessException{
		if(null == schemeName || "".equals(schemeName)||null == staffNo || "".equals(staffNo)){
			return null;
		}
		try{
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			PropertyFilter c = new PropertyFilter("ctrlSchemeName", schemeName);
			filters.add(c);
			PropertyFilter s = new PropertyFilter("staffNo", staffNo);
			filters.add(s);
		    return this.iWCtrlSchemeDao.findBy(filters);
		}
		catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
	/**
	 * 检查方案名称
	 * @param staffNo
	 * @param schemeType
	 * @param schemeName
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> checkSchemeName(String staffNo,
			String schemeType,String schemeName) throws DBAccessException{
		if (null == staffNo|| "".equals(staffNo)
			||null == schemeType || "".equals(schemeType)  	
			||null == schemeName || "".equals(schemeName)){
			return null;
		}
		try{
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			PropertyFilter s = new PropertyFilter("staffNo", staffNo);
			filters.add(s);
			PropertyFilter t = new PropertyFilter("ctrlSchemeType", schemeType);
			filters.add(t);
			PropertyFilter c = new PropertyFilter("ctrlSchemeName", schemeName);
			filters.add(c);
		    return this.iWCtrlSchemeDao.findBy(filters);
		}
		catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
	/**
	 * 根据方案Id查询某种类型方案
	 * @param schemeId
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
	 * 主要用来查询某种类型的所有方案
	 * @param schemeType
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByType(String staffNo,String schemeType) throws DBAccessException {
		if(null == schemeType || "".equals(schemeType)||null == staffNo || "".equals(staffNo)){
			return null;
		}
		try{
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			PropertyFilter c = new PropertyFilter("ctrlSchemeType", schemeType);
			filters.add(c);
			PropertyFilter s = new PropertyFilter("staffNo", staffNo);
			filters.add(s);
			return this.iWCtrlSchemeDao.findBy(filters);
		}
		catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 根据过滤条件查询方案
	 * @filters 
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByCond(List<PropertyFilter> filters) throws DBAccessException{
		if(null == filters || 0 == filters.size())
			return null;
		try{
			return this.iWCtrlSchemeDao.findBy(filters);
		}
		catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 新建控制方案
	 * @param ctrlSchemeName
	 * @param ctrlType
	 * @param staffNo
	 * @param createDate
	 * @param newStartDate
	 * @param newEndDate
	 * @param limitType
	 * @param ctrlLoad
	 * @param schemeRemark
	 * @return
	 */
	public void newScheme(String ctrlSchemeName, String ctrlType,
			String staffNo,String userOrgNo,Date newStartDate, Date newEndDate,
			String limitType,Double ctrlLoad,String schemeRemark)throws DBAccessException{
		try{
			WCtrlScheme scheme = new WCtrlScheme();
			scheme.setCtrlSchemeName(ctrlSchemeName);
			scheme.setCtrlSchemeType(ctrlType);
			scheme.setStaffNo(staffNo);
			scheme.setOrgNo(userOrgNo);
			scheme.setCreateDate(new Date());
			scheme.setCtrlDateStart(newStartDate);
			scheme.setCtrlDateEnd(newEndDate);
			scheme.setLimitType(limitType);
			scheme.setIsValid(1L);		
			scheme.setCtrlLoad(ctrlLoad);
			scheme.setSchemeRemark(schemeRemark);
			this.iWCtrlSchemeDao.save(scheme);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	   /**
     * 修改控制方案信息
     * @param ctrlSchemeId
     * @param ctrlSchemeName
     * @param schemeNameFlag
     * @param ctrlType
     * @param newStartDate
     * @param newEndDate
     * @param limitType
     * @param ctrlLoad
     * @param schemeRemark
     * @param staffNo
     * @return
     * @throws DBAccessException
     */
	public int updateScheme(Long ctrlSchemeId,String ctrlSchemeName, Integer schemeNameFlag,
			Date newStartDate, Date newEndDate,String limitType,Double ctrlLoad,
			String schemeRemark,String staffNo)throws DBAccessException{
		try{
			if(1==schemeNameFlag){
				//先查询该操作员是有重名方案
				List<WCtrlScheme> schemeList =this.querySchemesByName(staffNo,ctrlSchemeName);
				if(null != schemeList && 0 < schemeList.size())
					return 0;
			}
			List<WCtrlScheme> schemeList =this.querySchemesById(ctrlSchemeId);
	    	if(null==schemeList||0==schemeList.size())
	    		return 2;
	    	 WCtrlScheme scheme = schemeList.get(0);
	    	 scheme.setCtrlSchemeName(ctrlSchemeName);
	    	 scheme.setCtrlDateStart(dateToSqlDate(newStartDate));
			 scheme.setCtrlDateEnd(dateToSqlDate(newEndDate));
			 scheme.setLimitType(limitType);
			 scheme.setCtrlLoad(ctrlLoad);
			 scheme.setSchemeRemark(schemeRemark);
	    	 this.iWCtrlSchemeDao.update(scheme);
			 return 1;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	 /**
	 * date转化为数据库保存格式的date，如果有错返回当日信息
	 * @param date
	 * @return java.sql.Date
	 */
    private java.sql.Date dateToSqlDate(Date date){
    	if(null == date){
    		return new java.sql.Date(new Date().getTime());
    	}
    	try{
    	    return (new java.sql.Date(date.getTime()));
    	}catch(Exception ex){
    		return new java.sql.Date(new Date().getTime());
    	}
    }
}
