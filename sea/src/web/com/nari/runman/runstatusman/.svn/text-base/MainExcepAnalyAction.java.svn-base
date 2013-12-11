package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.advapp.statanalyse.ElecContrastAnalysisType;
import com.nari.basicdata.MainExceptionCategoryBean;
import com.nari.basicdata.OrgNoNameBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.statreport.MainExceptionBean;
import com.nari.statreport.TmnlRunStatus;
import com.nari.support.Page;

public class MainExcepAnalyAction extends BaseAction{

	private MainExcepAnalyManager mainExcepAnalyManager;
	private List<MainExceptionCategoryBean> mec;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean success = true;
 
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private String nodeType;
	private String orgNo;
	private String orgType;
	private String startDate;
	private String endDate;
	private String mainExcepCode;
	private String consNo;
	private String lineId;
	private String subsId;
	private String orgName;
	
	private List<MainExceptionBean> mainExcepAnalyList;

	public String queryMainExceptionCode() throws Exception {
		this.logger.debug("主站异常类型查询开始");
		this.setMec(this.mainExcepAnalyManager.getMainExceptionList());
		this.logger.debug("主站异常类型查询结束");
		return SUCCESS;
	}
	
	public String queryOrgName() throws Exception{
		this.logger.debug("主站异常orgName查询开始");
		this.setOrgName(this.mainExcepAnalyManager.getOrgName(orgNo));
		this.logger.debug("主站异常orgName查询结束");
		return SUCCESS;
	}
	
public String queryMainExcepAnalysis() throws Exception {
    if(nodeType==null||nodeType.equals("")){
    return ERROR;}
    Page<MainExceptionBean> pi = null;
    if(nodeType.equals("org")){
    	pi=this.mainExcepAnalyManager.findOrgNo(orgNo, orgType, startDate, endDate, mainExcepCode,start, limit);
    }
    else 
    if(nodeType.equals("usr")){
    	pi=this.mainExcepAnalyManager.findUsr(consNo,startDate,endDate,mainExcepCode,start,limit);
    }
    else
    if(nodeType.equals("line")){
    	pi=this.mainExcepAnalyManager.findLine(lineId,startDate,endDate,mainExcepCode,start,limit);
    }
    else
    if(nodeType.equals("sub")){
    	pi=this.mainExcepAnalyManager.findSub(subsId,startDate,endDate,mainExcepCode,start,limit);
    }
	if(pi!=null){
	this.setMainExcepAnalyList(pi.getResult());
	this.setTotalCount(pi.getTotalCount());
	}
	return SUCCESS;

	}
	
	
	public MainExcepAnalyManager getMainExcepAnalyManager() {
		return mainExcepAnalyManager;
	}

	public void setMainExcepAnalyManager(MainExcepAnalyManager mainExcepAnalyManager) {
		this.mainExcepAnalyManager = mainExcepAnalyManager;
	}
	
	public List<MainExceptionCategoryBean> getMec() {
		return mec;
	}

	public void setMec(List<MainExceptionCategoryBean> mec) {
		this.mec = mec;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public List<MainExceptionBean> getMainExcepAnalyList() {
		return mainExcepAnalyList;
	}

	public void setMainExcepAnalyList(List<MainExceptionBean> mainExcepAnalyList) {
		this.mainExcepAnalyList = mainExcepAnalyList;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMainExcepCode() {
		return mainExcepCode;
	}

	public void setMainExcepCode(String mainExcepCode) {
		this.mainExcepCode = mainExcepCode;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


}
