package com.nari.runman.runstatusman;

//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.statreport.TmnlFactory;
import com.nari.statreport.TmnlRunRemark;
import com.nari.statreport.TmnlRunStatus;
import com.nari.statreport.TmnlRunStatusRun;
import com.nari.statreport.TmnlTypeCode;
import com.nari.support.Page;

public class TmnlRunStatusAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private TmnlRunStatusManager tmnlRunStatusManager;

	private List<TmnlRunStatus> tmnlRunStatusList;
	private List<TmnlRunStatusRun> tmnlRunStatusRunList;
	private List<TmnlRunRemark> tmnlRunRemarkList;

	private boolean success = true;
	private long totalCount;

	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	public Date startDate;
	public Date endDate;
	private String orgNo;
	private String orgName;
	private String consNo;
	private String orgType;
	private String nodeType; // 节点类型
	private String groupNo; // 组号:普通群组ugp控制群组cgp
	private String subsId; // 变电站标识
	private String lineId; // 线路编号
	//终端在线状态
	private String isOnline;
	//厂商代码
	private String factoryCode;
	// 终端类型  	
	private String tmnlTypeCode;
	private String commMode;//通信方式
	private String tmnlFactory;//终端厂商
	private List<TmnlFactory> factoryList;//所有终端厂商
	private List<TmnlTypeCode> tmnlTypeCodeList;//终端类型

	/**
	 * @return 备注
	 * @throws Exception 
	 */
	public String queryRemark()throws Exception {
		
		Page<TmnlRunRemark> bz = null;
		bz = tmnlRunStatusManager.findRemark(consNo, start, limit);
		if(null!=bz){
			tmnlRunRemarkList = bz.getResult();
	    	totalCount = bz.getTotalCount();
    	}
    	return SUCCESS;
	}
	
	/**
	 * @return 备注信息保存
	 * @throws Exception 
	 */
	public String updateRemark() throws Exception {

		tmnlRunStatusManager.updateRemark(exceptionId, remark);
		return SUCCESS;
	}

	/******
	 * 异常工况页面 点击左边树节 获取数据方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTerminalRunStatus() throws Exception {
		
//		String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
//		String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		
		
		if (null == nodeType || "".equals(nodeType)) {
			return ERROR;
		}
		Page<TmnlRunStatus> trs = null;
//		String nodeValue = null;
//		PSysUser userInfo = this.getPSysUser();
//		trs = tmnlRunStatusManager.queryPageTmnl(nodeType, nodeValue, tmnlTypeCode, userInfo, start, limit, startDate, endDate);

		if ("org".equals(nodeType)) { // 定义org需要orgType,其他的不用
			trs = tmnlRunStatusManager.findOrgNo(orgNo, orgType, startDate, endDate, tmnlTypeCode,start, limit);
		} else if ("usr".equals(nodeType)) {
			trs = tmnlRunStatusManager.findUsr(consNo, startDate, endDate, tmnlTypeCode,start, limit);
		} else if ("line".equals(nodeType)) {
			trs = tmnlRunStatusManager.findLine(lineId, startDate, endDate, tmnlTypeCode, start, limit);
		} else if ("cgp".equals(nodeType)) {
			trs = tmnlRunStatusManager.findCgp(groupNo, startDate, endDate, tmnlTypeCode, start, limit);
		} else if ("ugp".equals(nodeType)) {
			trs = tmnlRunStatusManager.findUgp(groupNo, startDate, endDate, tmnlTypeCode, start, limit);
		} else if ("sub".equals(nodeType)) {
			trs = tmnlRunStatusManager.findSub(subsId, startDate, endDate, tmnlTypeCode, start, limit);
		} else {
			return SUCCESS;
		}
		if (null != trs) {
			tmnlRunStatusList = trs.getResult();
			totalCount = trs.getTotalCount();
		}
		return SUCCESS;
	}

	/*****
	 * 实时工况页面查询 点击左边树节 获取数据方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTerminalRunStatusRun() throws Exception {

		if (null == nodeType || "".equals(nodeType)) {
			return ERROR;
		}
		Page<TmnlRunStatusRun> trsR = null;
		PSysUser user=(PSysUser) getSession().getAttribute("pSysUser");

		if ("org".equals(nodeType)) { // 定义org需要orgType,其他的不用
			trsR = tmnlRunStatusManager.findOrgNoRun(orgNo, orgType, isOnline, tmnlTypeCode,commMode,tmnlFactory, start,limit);
		} else if ("usr".equals(nodeType)) {
			trsR = tmnlRunStatusManager.findUsrRun(consNo,  tmnlTypeCode,isOnline,start, limit);
		} else if ("line".equals(nodeType)) {
			trsR = tmnlRunStatusManager.findLineRun(lineId, tmnlTypeCode,isOnline,commMode,tmnlFactory, start, limit);
		} else if ("cgp".equals(nodeType)) {
			trsR = tmnlRunStatusManager.findCgpRun(groupNo,  tmnlTypeCode,isOnline,commMode,tmnlFactory,start, limit);
		} else if ("ugp".equals(nodeType)) {
			trsR = tmnlRunStatusManager.findUgpRun(groupNo, tmnlTypeCode, isOnline,commMode,tmnlFactory,start, limit);
		} else if ("sub".equals(nodeType)) {
			trsR = tmnlRunStatusManager.findSubRun(subsId,  tmnlTypeCode,isOnline,commMode,tmnlFactory, start, limit);
		} else if("fac".equals(nodeType)){
			trsR = tmnlRunStatusManager.findFacRun(factoryCode,user,  tmnlTypeCode, isOnline,start, limit);
		}
		if (null != trsR) {
			tmnlRunStatusRunList = trsR.getResult();
			totalCount = trsR.getTotalCount();
		}
		return SUCCESS;
	}

	/**
	 * 查询所有终端厂商
	 * chenjg
	 * @return
	 */
	public String queryTmnlFactory(){
		try {
			factoryList = tmnlRunStatusManager.getAllFactory();
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 查询所有终端类型
	 * @return
	 */
	public String queryTmnlTypeCode()throws Exception{
		try {
			setTmnlTypeCodeList(tmnlRunStatusManager.queryTmnlTypeCode());
		} catch (Exception e) {
			logger.error("查询所有终端类型 操作出错!");
			return ERROR;
		}
		return SUCCESS;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setTmnlRunStatusManager(
			TmnlRunStatusManager tmnlRunStatusManager) {
		this.tmnlRunStatusManager = tmnlRunStatusManager;
	}

	public List<TmnlRunStatus> getTmnlRunStatusList() {
		return tmnlRunStatusList;
	}

	public void setTmnlRunStatusList(List<TmnlRunStatus> tmnlRunStatusList) {
		this.tmnlRunStatusList = tmnlRunStatusList;
	}

	public void setTmnlRunStatusRunList(
			List<TmnlRunStatusRun> tmnlRunStatusRunList) {
		this.tmnlRunStatusRunList = tmnlRunStatusRunList;
	}

	public List<TmnlRunStatusRun> getTmnlRunStatusRunList() {
		return tmnlRunStatusRunList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public void setTmnlRunRemarkList(List<TmnlRunRemark> tmnlRunRemarkList) {
		this.tmnlRunRemarkList = tmnlRunRemarkList;
	}


	public List<TmnlRunRemark> getTmnlRunRemarkList() {
		return tmnlRunRemarkList;
	}

	private Long exceptionId;
	private String remark;
	
	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}


	public Long getExceptionId() {
		return exceptionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}

	public String getTmnlTypeCode() {
		return tmnlTypeCode;
	}

	public void setTmnlTypeCode(String tmnlTypeCode) {
		this.tmnlTypeCode = tmnlTypeCode;
	}

	/**
	 *  the tmnlTypeCodeList
	 * @return the tmnlTypeCodeList
	 */
	public List<TmnlTypeCode> getTmnlTypeCodeList() {
		return tmnlTypeCodeList;
	}

	/**
	 *  the tmnlTypeCodeList to set
	 * @param tmnlTypeCodeList the tmnlTypeCodeList to set
	 */
	public void setTmnlTypeCodeList(List<TmnlTypeCode> tmnlTypeCodeList) {
		this.tmnlTypeCodeList = tmnlTypeCodeList;
	}

	public List<TmnlFactory> getFactoryList() {
		return factoryList;
	}

	public void setFactoryList(List<TmnlFactory> factoryList) {
		this.factoryList = factoryList;
	}

	public String getCommMode() {
		return commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getTmnlFactory() {
		return tmnlFactory;
	}

	public void setTmnlFactory(String tmnlFactory) {
		this.tmnlFactory = tmnlFactory;
	}
	

}
