package com.nari.baseapp.planpowerconsume;

import java.util.List;
import java.util.Map;

import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.basicdata.VwLimitType;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * @author 陈建国
 *
 * @创建时间:2010-1-7 上午10:14:15
 *
 * @类描述:有序用电任务编制
 *	
 */
public interface IPlanCompileManager {

	public Page <WCtrlScheme> getAllWCtrlScheme(Page page) throws DBAccessException;
	
	
	/**
	 * 功控类型
	 * @return
	 * @throws DBAccessException
	 */
	public List<VwCtrlSchemeType> getAllCtrlSchemeType() throws DBAccessException;
	/***
	 * 通过多条件查询来进行查询
	 *  @return 分页对象
	 * @throws DBAccessException
	 * ****/
	public Page <WCtrlScheme> findWCtrlSheme(PSysUser staff, WCtrlScheme scheme,long start, int limit) throws DBAccessException;
	/***
	 * 查找所有的用电限制
	 * ****/
	public List<VwLimitType> findAllLimitType();
	/***
	 * 
	 * 通过一个记录编号的列表来得到列表和机构名称的键值对
	 * ***/
	public Map findOrgNoToName(List nos);
}
