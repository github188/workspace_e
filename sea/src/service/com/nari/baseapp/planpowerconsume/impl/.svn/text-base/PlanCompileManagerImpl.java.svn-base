package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;
import java.util.Map;

import com.nari.baseapp.planpowerconsume.IPlanCompileManager;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeJdbcDao;
import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.basicdata.VwCtrlSchemeTypeDao;
import com.nari.basicdata.VwLimitType;
import com.nari.basicdata.VwLimitTypeJdbcDao;
import com.nari.oranization.IOrgJdbcDao;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * @author 陈建国
 *
 * @创建时间:2010-1-7 上午10:18:57
 *
 * @类描述:有序用电任务编制
 *	
 */
public class PlanCompileManagerImpl implements IPlanCompileManager {


	private IWCtrlSchemeDao iWCtrlSchemeDao;
	private IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao;
	private  VwCtrlSchemeTypeDao vwCtrlSchemeTypeDao;
	private IOrgJdbcDao iOrgJdbcDao;
	
	public void setiOrgJdbcDao(IOrgJdbcDao iOrgJdbcDao) {
		this.iOrgJdbcDao = iOrgJdbcDao;
	}
	public void setiWCtrlSchemeJdbcDao(IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao) {
		this.iWCtrlSchemeJdbcDao = iWCtrlSchemeJdbcDao;
	}
	/**
	 * 用来取得所有的限制类型
	 * **/
	private VwLimitTypeJdbcDao vwLimitTypeJdbcDao;

	public void setVwLimitTypeJdbcDao(VwLimitTypeJdbcDao vwLimitTypeJdbcDao) {
		this.vwLimitTypeJdbcDao = vwLimitTypeJdbcDao;
	}

	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}

	public void setVwCtrlSchemeTypeDao(VwCtrlSchemeTypeDao vwCtrlSchemeTypeDao) {
		this.vwCtrlSchemeTypeDao = vwCtrlSchemeTypeDao;
		
	}
	
	public List<VwLimitType> findAllLimitType(){
		return this.vwLimitTypeJdbcDao.findAll();
	}

	/* (non-Javadoc)
	 * @see com.nari.baseapp.planpowerconsume.IPlanComplileManager#getAllWCtrlScheme()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<WCtrlScheme> getAllWCtrlScheme(Page page) throws DBAccessException {
		return iWCtrlSchemeDao.findAllWCtrlScheme(page);
	}

	@Override
	public List<VwCtrlSchemeType> getAllCtrlSchemeType() throws DBAccessException{
		return this.vwCtrlSchemeTypeDao.findAll();
	}

	public Page<WCtrlScheme> findWCtrlSheme(PSysUser staff, WCtrlScheme scheme,long start, int limit) throws DBAccessException {
		return this.iWCtrlSchemeJdbcDao.findPage(staff, scheme,start,limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map findOrgNoToName(List nos) {
	return	iOrgJdbcDao.findNoToName(nos);
	}
}
