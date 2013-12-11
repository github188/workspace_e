package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 线路电量查询action
 * @author ChunMingLi
 * @since  2010-6-23
 *
 */
public class LineEceltricQuantityAction extends BaseAction {
	//记录日志
	private static final Logger logger = Logger.getLogger(LineEceltricQuantityAction.class);
	
	private boolean success = true;
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	
	//分页页码
	private long start = 0;
	
	//每页显示数
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	
	//页面总数地区
	private long totalCount;
	
	//查询开始时间
	private Date startDate;
	
	//查询结束时间
	private Date endDate;
	
	//线路ID
	private String lineId;
	
	//线路电量列表
	private List<LineEceltricQuantityDto> lineList;
	
	//线路电量用户列表
	private List<LineEcelUserDto> lineUserList;
	
	private LineEceltricQuantityManager lineEceltricQuantityManager;

	
	
	
	/**
	 *  the lineEceltricQuantityManager to set
	 * @param lineEceltricQuantityManager the lineEceltricQuantityManager to set
	 */
	public void setLineEceltricQuantityManager(
			LineEceltricQuantityManager lineEceltricQuantityManager) {
		this.lineEceltricQuantityManager = lineEceltricQuantityManager;
	}

	/**
	 * 根据左边树节点和时间查询异常
	 * 
	 * @throws Exception
	 */
	public String queryLineEcel() throws Exception {
		logger.debug("queryLineEcel : 线路电量查询开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		Page<LineEceltricQuantityDto> pageInfo = this.lineEceltricQuantityManager.queryPageLineEcel(nodeType, nodeValue, userInfo, start, limit, startDate, endDate);
		if(pageInfo == null){
//			this.success=false;
			return SUCCESS;
		}
		this.setLineList(pageInfo.getResult());
		this.setTotalCount(pageInfo.getTotalCount());
		logger.debug("queryDeException : 线路电量查询结束");
		return SUCCESS;
	}

	/**
	 * 线路电量用户查询集合
	 * 
	 * @throws Exception
	 */
	public String queryLineEcelUser() throws Exception {
		logger.debug("queryLineEcelUser : 线路电量用户查询开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute("pSysUser");
		Page<LineEcelUserDto> pageInfo = this.lineEceltricQuantityManager.queryPageLineEcelUser(lineId, userInfo, start, limit, startDate, endDate);
		if(pageInfo == null){
			return SUCCESS;
		}
		this.setLineUserList(pageInfo.getResult());
		this.setTotalCount(pageInfo.getTotalCount());
		logger.debug("queryLineEcelUser : 线路电量用户查询结束");
		return SUCCESS;
	}
	
	/**
	 *  the lineList
	 * @return the lineList
	 */
	public List<LineEceltricQuantityDto> getLineList() {
		return lineList;
	}

	/**
	 *  the lineList to set
	 * @param lineList the lineList to set
	 */
	public void setLineList(List<LineEceltricQuantityDto> lineList) {
		this.lineList = lineList;
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
	 *  the lineId
	 * @return the lineId
	 */
	public String getLineId() {
		return lineId;
	}

	/**
	 *  the lineId to set
	 * @param lineId the lineId to set
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	/**
	 *  the lineUserList
	 * @return the lineUserList
	 */
	public List<LineEcelUserDto> getLineUserList() {
		return lineUserList;
	}

	/**
	 *  the lineUserList to set
	 * @param lineUserList the lineUserList to set
	 */
	public void setLineUserList(List<LineEcelUserDto> lineUserList) {
		this.lineUserList = lineUserList;
	}
	
}
