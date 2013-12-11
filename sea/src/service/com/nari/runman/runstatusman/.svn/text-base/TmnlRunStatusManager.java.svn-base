package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.statreport.TmnlFactory;
import com.nari.statreport.TmnlRunRemark;
import com.nari.statreport.TmnlRunStatus;
import com.nari.statreport.TmnlRunStatusRun;
import com.nari.statreport.TmnlTypeCode;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface TmnlRunStatusManager {
	/**
	 * @param start
	 * @param limit
	 * @return 备注
	 */
	public Page<TmnlRunRemark> findRemark(String consNo, long start, int limit)
			throws DBAccessException;
	
	/**
	 * @return 备注信息保存
	 * @param exceptionId
	 * @param remark
	 * @throws DBAccessException
	 */
	public void updateRemark(Long exceptionId, String remark)
			throws DBAccessException;
	
	//	************异常工况页面
	// ------点击左边树
	/**
	 * @param
	 * @return 点击orgType查询
	 */
	public Page<TmnlRunStatus> findOrgNo(String orgNo, String orgType, Date startDate, Date endDate,String tmnlTypeCode,long start,
			int limit) throws DBAccessException;

	/**
	 * @param
	 * @return 点击usr查询
	 */
	public Page<TmnlRunStatus> findUsr(String consNo, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException;

	/**
	 * @param
	 * @return 点击line查询
	 */
	public Page<TmnlRunStatus> findLine(String lineId, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException;

	/**
	 * @param
	 * @return 点击cgp查询
	 */
	public Page<TmnlRunStatus> findCgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode,long start,
			int limit) throws DBAccessException;
	
	/**
	 * @param
	 * @return 点击ugp查询
	 */
	public Page<TmnlRunStatus> findUgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode,long start,
			int limit) throws DBAccessException;

	/**
	 * @param
	 * @return 点击sub查询
	 */
	public Page<TmnlRunStatus> findSub(String subsId, Date startDate, Date endDate,String tmnlTypeCode,long start, int limit)
			throws DBAccessException;
	
	
	
	//**********实时工况页面
	/**
	 * @param
	 * @return 点击orgType查询
	 */
	public Page<TmnlRunStatusRun> findOrgNoRun(String orgNo, String orgType, String isOnline,String tmnlTypeCode,String commCode,String tmnlFactory, long start,
			int limit) throws DBAccessException;

	/**
	 * @param
	 * @return 点击usr查询
	 */
	public Page<TmnlRunStatusRun> findUsrRun(String consNo, String tmnlTypeCode,String isOnline,long start, int limit)
			throws DBAccessException;

	/**
	 * @param
	 * @return 点击line查询
	 */
	public Page<TmnlRunStatusRun> findLineRun(String lineId, String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory,long start, int limit)
			throws DBAccessException;

	/**
	 * @param
	 * @return 点击cgp查询
	 */
	public Page<TmnlRunStatusRun> findCgpRun(String groupNo,String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory, long start,
			int limit) throws DBAccessException;
	
	/**
	 * @param
	 * @return 点击ugp查询
	 */
	public Page<TmnlRunStatusRun> findUgpRun(String groupNo, String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory,long start,
			int limit) throws DBAccessException;

	/**
	 * @param
	 * @return 点击sub查询
	 */
	public Page<TmnlRunStatusRun> findSubRun(String subsId, String tmnlTypeCode,String isOnline,String commCode,String tmnlFactory,long start, int limit)
			throws DBAccessException;
	
	/**
	 * 
	 *  根据厂商id查询
	 * 
	 * @param factoryCode  厂商代码
	 * @param tmnlTypeCode 终端类型
	 * @param start		   	页码
	 * @param limit			每页显示数
	 * @return				返回列表
	 * @throws DBAccessException  
	 */
	public Page<TmnlRunStatusRun> findFacRun(String factoryCode,PSysUser user, String tmnlTypeCode,String isOnline,long start, int limit)
			throws DBAccessException;
	/**
	 * chenjg
	 * 查询所有生产厂商
	 * @return
	 * @throws Exception
	 */
	public List<TmnlFactory> getAllFactory() throws Exception;
	
	public Page<TmnlRunStatus> queryPageTmnl(String nodeType, String nodeValue, String tmnlTypeCode, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) throws Exception;

	/**
	 * 
	 * 查询终端类型
	 * @return 类型集合
	 * @throws Exception 
	 */
	public List<TmnlTypeCode> queryTmnlTypeCode() throws Exception;
}
