package com.nari.qrystat.querycollpoint;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.impl.ExcptionReportManagerImpl;
import com.nari.support.Page;

public class ExcptionReportAction extends BaseAction {
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private List<ExcptionReport> exceptionReportList;
	private Logger logger = Logger.getLogger(this.getClass());
	private ExcptionReportManagerImpl excptionReportManager;
	private String orgNo;
	private String orgType;
	private PSysUser pSysUser;
	private String dateStart;
	private String dateEnd;
	private List<VwConsType> consTypeList;
	private String treeType;
	
	
	
	public String getTreeType() {
		return treeType;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
	public ExcptionReportManagerImpl getExcptionReportManager() {
		return excptionReportManager;
	}
	public void setExcptionReportManager(
			ExcptionReportManagerImpl excptionReportManager) {
		this.excptionReportManager = excptionReportManager;
	}
	public List<VwConsType> getConsTypeList() {
		return consTypeList;
	}
	public void setConsTypeList(List<VwConsType> consTypeList) {
		this.consTypeList = consTypeList;
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<ExcptionReport> getExceptionReportList() {
		return exceptionReportList;
	}
	public void setExceptionReportList(List<ExcptionReport> exceptionReportList) {
		this.exceptionReportList = exceptionReportList;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public PSysUser getpSysUser() {
		return pSysUser;
	}
	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	/**
	 * 异常告警统计
	 * @return
	 * @throws Exception
	 */
	public String queryexceptionReport() throws Exception {
		try {
			this.logger.debug(" 异常告警统计");
			pSysUser =super.getPSysUser();
			Page<ExcptionReport> exceptionReportPage = this.excptionReportManager.findExcptionCount(treeType,orgNo, orgType, pSysUser, dateStart, dateEnd, start, limit);
			if(exceptionReportPage != null){
				exceptionReportList = exceptionReportPage.getResult();
				totalCount = exceptionReportPage.getTotalCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 异常告警统计
	 * @return
	 * @throws Exception
	 */
	public String queryConsType() throws Exception {
		try {
			this.logger.debug(" 异常告警统计");
			consTypeList = this.excptionReportManager.findConsType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
