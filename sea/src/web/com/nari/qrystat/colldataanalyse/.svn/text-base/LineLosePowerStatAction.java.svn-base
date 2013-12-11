package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 线路用电损耗统计Action
 * @author 姜炜超
 */
public class LineLosePowerStatAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(LineLosePowerStatAction.class);
	
	private LineLosePowerStatManager lineLosePowerStatManager;
	
	//成功返回标记
	public boolean success = true;
	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	private String nodeId;//节点id
	private String nodeType;//节点类型
	private String orgType;//单位类型
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String statFlag;//02表示月，03表示季度，04表示年
	private List<LineLosePowerBean> linelpsList;//线路用电损耗统计数据列表
	
	/**
	 * 加载线路用电损耗统计数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("查询线路用电损耗统计开始");
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	Page<LineLosePowerBean> page = lineLosePowerStatManager.findLineLosePowerStat(nodeId, nodeType,
    			orgType, statFlag, startDate, endDate, start, limit, pSysUser);
    	linelpsList = page.getResult();
    	totalCount = page.getTotalCount();
    	
    	logger.debug("查询线路用电损耗统计结束");
    	return SUCCESS;
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

	public String getStatFlag() {
		return statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}

	public List<LineLosePowerBean> getLinelpsList() {
		return linelpsList;
	}

	public void setLinelpsList(List<LineLosePowerBean> linelpsList) {
		this.linelpsList = linelpsList;
	}

	public void setLineLosePowerStatManager(
			LineLosePowerStatManager lineLosePowerStatManager) {
		this.lineLosePowerStatManager = lineLosePowerStatManager;
	}
}
