package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.IAuthorizeManager;
import com.nari.util.DateUtil;
import com.nari.util.exception.DBAccessException;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用电客户排名分析Action
 * @author 姜炜超
 */
public class PowerSortAnalyseAction extends BaseAction {
	//定义日志
	private static final Logger logger = Logger.getLogger(PowerSortAnalyseAction.class);
	
	private PowerSortAnalyseManager powerSortAnalyseManager;
	private IAuthorizeManager iAuthorizeManager;
    //请求返回列表
	private boolean success = true;
	private long totalCount;
   //前台参数
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	
	private String nodeId;//节点id
	private String nodeType;//节点类型
	private String orgType;//供电单位类别，如果节点是供电单位，则该字段有效
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private int sort;//排名
	private List<PowerSortAnalyseDto> psaList;//用电客户排名信息列表
	private Double totalPapE;//总用电量
	private Double sortPapE;//排名前n位客户的用电量
	private OOrg org ;//组织机构信息
	
	/**
	 * 查询用电客户排名分析数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("查询用电客户排名分析开始");
    	
    	if(null == nodeId || "".equals(nodeId) || 
    			null == startDate || null == endDate ||
    			null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
		String startDate = DateUtil.dateToString(this.startDate);
		String endDate = DateUtil.dateToString(this.endDate);
    	
    	Page<PowerSortAnalyseDto> psc = null;
    	    	
    	psc = powerSortAnalyseManager.findPSAInfoByCond(nodeId, nodeType, orgType, startDate, endDate, sort, start, limit, pSysUser);
        
    	psaList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询用电客户排名分析结束");
    	return SUCCESS;
    }
    
	/**
	 * 加载图形1数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadChartData() throws Exception{
    	logger.debug("查询用电客户排名分析图形数据开始");
    	if(null == nodeId || "".equals(nodeId) || 
    			null == startDate || null == endDate ||
    			null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
		String startDate = DateUtil.dateToString(this.startDate);
		String endDate = DateUtil.dateToString(this.endDate);
		this.totalPapE = powerSortAnalyseManager.queryPSATotalPower(nodeId, nodeType, orgType, startDate, endDate, pSysUser);
    	this.sortPapE = powerSortAnalyseManager.queryPSASortPower(nodeId, nodeType, orgType, startDate, endDate, sort, pSysUser);
    	logger.debug("查询用电客户排名分析图形数据结束");
    	return SUCCESS;
    }
    
    /**
     * @desc 根据OrgNo获取组织机构信息
     * @return
     */
    public String getOOrgById(){
    	PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
    	String orgNo = user.getOrgNo();
    	try {
			org = iAuthorizeManager.getOrgById(orgNo);
		} catch (DBAccessException e) {
			logger.debug("获取组织机构信息失败！");
			e.printStackTrace();
		}
    	return SUCCESS;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setPowerSortAnalyseManager(
			PowerSortAnalyseManager powerSortAnalyseManager) {
		this.powerSortAnalyseManager = powerSortAnalyseManager;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<PowerSortAnalyseDto> getPsaList() {
		return psaList;
	}

	public void setPsaList(List<PowerSortAnalyseDto> psaList) {
		this.psaList = psaList;
	}

	public Double getTotalPapE() {
		return totalPapE;
	}

	public void setTotalPapE(Double totalPapE) {
		this.totalPapE = totalPapE;
	}

	public Double getSortPapE() {
		return sortPapE;
	}

	public void setSortPapE(Double sortPapE) {
		this.sortPapE = sortPapE;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public void setiAuthorizeManager(IAuthorizeManager iAuthorizeManager) {
		this.iAuthorizeManager = iAuthorizeManager;
	}

	public OOrg getOrg() {
		return org;
	}

	public void setOrg(OOrg org) {
		this.org = org;
	}

}
