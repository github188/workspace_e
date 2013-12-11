package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 线路用电损耗查询Action
 * @author 姜炜超
 */
public class LineLosePowerQryAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(LineLosePowerQryAction.class);
	
	private LineLosePowerQryManager lineLosePowerQryManager;
	
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
	private String orgNo; //单位编码
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private List<LineLosePowerBean> linelpqList;//线路查询列表
	private List<LineLosePowerReadBean> linelpqReadList;//时间段内线路用户抄表信息列表
	
	private String lineId;//线路号
	private Boolean flag;//成功or失败
	private String ioValue;//入口or出口
	private Boolean checkAll;//是否全选
	private Boolean checkRecall;//是否补召
	private Boolean checkRecalc;//是否重新计算
	private List<String> tmnlAssetNoList;//资产编号列表
	
	 /**
	 * 加载线路用电损耗查询数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("线路用电损耗查询开始");
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	Page<LineLosePowerBean> page = lineLosePowerQryManager.findLineLosePowerQry(nodeId, nodeType,
    			orgType, startDate, endDate, start, limit, pSysUser);
    	linelpqList = page.getResult();
    	totalCount = page.getTotalCount();
    	
    	logger.debug("线路用电损耗查询结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载时间段内线路用户抄表信息
	 * @return String 
	 * @throws Exception
	 */
    public String loadReadData() throws Exception{
    	logger.debug("查询时间段内线路用户抄表信息开始");
    	    	    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
		
    	Page<LineLosePowerReadBean> psc = lineLosePowerQryManager.findLineLPReadInfo(lineId, startDate, flag, 
    			ioValue, endDate, start, limit, pSysUser);

    	linelpqReadList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	
    	logger.debug("查询时间段内线路用户抄表信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 执行操作，补召
	 * @return String 
	 * @throws Exception
	 */
    public String exec() throws Exception{
    	if(null == tmnlAssetNoList || 0 == tmnlAssetNoList.size()){
    		return SUCCESS;
    	}
    	logger.debug("补召执行开始");
    	
    	if(checkRecall){
    		lineLosePowerQryManager.recallData(checkAll, lineId, startDate, flag, 
        			ioValue, endDate, tmnlAssetNoList);//补召
    	}
    	
    	logger.debug("补召执行结束");
    	return SUCCESS;
    }
    
    /**
	 * 执行操作，重新计算
	 * @return String 
	 * @throws Exception
	 */
    public String recalcData() throws Exception {
    	logger.debug("计算开始");
    	if(checkRecalc){
    	    this.lineLosePowerQryManager.recalcData(orgNo, startDate, endDate);//重新计算
    	}
    	logger.debug("计算结束");
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
	public void setLineLosePowerQryManager(
			LineLosePowerQryManager lineLosePowerQryManager) {
		this.lineLosePowerQryManager = lineLosePowerQryManager;
	}

	public List<LineLosePowerBean> getLinelpqList() {
		return linelpqList;
	}

	public void setLinelpqList(List<LineLosePowerBean> linelpqList) {
		this.linelpqList = linelpqList;
	}

	public List<LineLosePowerReadBean> getLinelpqReadList() {
		return linelpqReadList;
	}

	public void setLinelpqReadList(List<LineLosePowerReadBean> linelpqReadList) {
		this.linelpqReadList = linelpqReadList;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getIoValue() {
		return ioValue;
	}

	public void setIoValue(String ioValue) {
		this.ioValue = ioValue;
	}

	public Boolean getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(Boolean checkAll) {
		this.checkAll = checkAll;
	}

	public Boolean getCheckRecall() {
		return checkRecall;
	}

	public void setCheckRecall(Boolean checkRecall) {
		this.checkRecall = checkRecall;
	}

	public Boolean getCheckRecalc() {
		return checkRecalc;
	}

	public void setCheckRecalc(Boolean checkRecalc) {
		this.checkRecalc = checkRecalc;
	}

	public List<String> getTmnlAssetNoList() {
		return tmnlAssetNoList;
	}

	public void setTmnlAssetNoList(List<String> tmnlAssetNoList) {
		this.tmnlAssetNoList = tmnlAssetNoList;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
}
