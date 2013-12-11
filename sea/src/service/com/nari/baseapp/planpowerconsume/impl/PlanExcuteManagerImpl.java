package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.IPlanExcuteManager;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeJdbcDao;
import com.nari.support.Page;

/***
 * 有序用电任务执行 实现类
 * 
 * @author huangxuan *
 **/
public class PlanExcuteManagerImpl implements IPlanExcuteManager {
	private IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao;

	public void setiWCtrlSchemeJdbcDao(IWCtrlSchemeJdbcDao iWCtrlSchemeJdbcDao) {
		this.iWCtrlSchemeJdbcDao = iWCtrlSchemeJdbcDao;
	}
	/***
	 * 找到控制方案编号和控制方案名称之间的联系 
	 * 返回分好页的结果
	 * ***/
	public Page findSchemeNoNames(String staffNo, long start, int limit) {
		return this.iWCtrlSchemeJdbcDao.findAllSchemeNames(staffNo, start,
				limit);
	}
	//控制参数设置
	/***
	 * 
	 * ***/
}
