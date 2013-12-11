package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.advapp.statanalyse.CollectionPointDemandDto;
import com.nari.advapp.statanalyse.CollectionPointDemandManager;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 测量点需量分析action
 * 
 * @author ChunMingLi
 * @since  2010-7-27
 *
 */
public class CollectionPointDemandAction extends BaseAction {
	//记录日志
	private  Logger logger = Logger.getLogger(CollectionPointDemandAction.class);
	//注入的manager
	private CollectionPointDemandManager collectionPointDemandManager;
	
	/**
	 *  the collectionPointDemandManager to set
	 * @param collectionPointDemandManager the collectionPointDemandManager to set
	 */
	public void setCollectionPointDemandManager(
			CollectionPointDemandManager collectionPointDemandManager) {
		this.collectionPointDemandManager = collectionPointDemandManager;
	}
	
	//分页参数
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	//成功事件
	private boolean success = true;
	
	//查询开始时间
	private Date startDate;
	
	//查询结束时间
	private Date endDate;
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	
	private String orgType;
	
	//按月测量点需量分析
	private List<CollectionPointDemandDto> cpdMonthList;
	//按日测量点需量分析
	private List<CollectionPointDemandDto> cpdDateList;
	
	/**
	 * 查询按日测量点需量分析
	 * @return
	 * @throws Exception 
	 */
	public String  queryCollectionPointDemandDate() throws Exception{
		logger.debug("queryCollectionPointDemandDate : 查询按日测量点需量分析开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
//		try {
			Page<CollectionPointDemandDto> cpdpage =  collectionPointDemandManager.queryCollectionPointDemandDate(orgType,nodeType, nodeValue, userInfo, start, limit, startDate, endDate);
			if(cpdpage != null){
				setTotalCount(cpdpage.getTotalCount());
				setCpdDateList(cpdpage.getResult());
			}
//		} catch (Exception e) {
//			logger.error("查询按日测量点需量分析 操作出错.  \n" + e);
//		}
		logger.debug("queryCollectionPointDemandDate : 查询按日测量点需量分析结束");
		return SUCCESS;
		
	}
	/**
	 * 查询按月测量点需量分析
	 * @return
	 * @throws Exception 
	 */
	public String  queryCollectionPointDemandMonth() throws Exception{
		logger.debug("queryCollectionPointDemandMonth : 查询按月测量点需量分析开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
//		try {
			Page<CollectionPointDemandDto> cpdpage = collectionPointDemandManager.queryCollectionPointDemandMonth(orgType,nodeType, nodeValue, userInfo, start, limit, startDate, endDate);
			if(cpdpage != null){
				setTotalCount(cpdpage.getTotalCount());
				setCpdMonthList(cpdpage.getResult());
			}
//		} catch (Exception e) {
//			logger.error("查询按月测量点需量分析 操作出错.  \n" + e);
//		}
		logger.debug("queryCollectionPointDemandDate : 查询按月测量点需量分析结束");
		return SUCCESS;
		
	}
	/**
	 *  the totalCount
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 *  the totalCount to set
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 *  the start
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 *  the start to set
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 *  the limit
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 *  the limit to set
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 *  the success
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 *  the success to set
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 *  the startDate
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 *  the startDate to set
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 *  the endDate
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 *  the endDate to set
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 *  the cpdMonthList
	 * @return the cpdMonthList
	 */
	public List<CollectionPointDemandDto> getCpdMonthList() {
		return cpdMonthList;
	}
	/**
	 *  the cpdMonthList to set
	 * @param cpdMonthList the cpdMonthList to set
	 */
	public void setCpdMonthList(List<CollectionPointDemandDto> cpdMonthList) {
		this.cpdMonthList = cpdMonthList;
	}
	/**
	 *  the cpdDateList
	 * @return the cpdDateList
	 */
	public List<CollectionPointDemandDto> getCpdDateList() {
		return cpdDateList;
	}
	/**
	 *  the cpdDateList to set
	 * @param cpdDateList the cpdDateList to set
	 */
	public void setCpdDateList(List<CollectionPointDemandDto> cpdDateList) {
		this.cpdDateList = cpdDateList;
	}
	/**
	 *  the nodeType
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 *  the nodeType to set
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 *  the nodeValue
	 * @return the nodeValue
	 */
	public String getNodeValue() {
		return nodeValue;
	}
	/**
	 *  the nodeValue to set
	 * @param nodeValue the nodeValue to set
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	/**
	 *  the orgType
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}
	/**
	 *  the orgType to set
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	

}
