package com.nari.advapp.statanalyse;

import java.util.Date;
import java.util.List;

import uk.ltd.getahead.dwr.util.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.support.Page;
/**
 * @desc 线路统计分析
 * @author chenguozhang
 *
 */
public class LineStatAnalysisAction extends BaseAction {
	Logger log=Logger.getLogger(LineStatAnalysisAction.class);
	private LineStatAnalysisManager lineStatAnalysisManager;
	private boolean success=true;
	private String type;
	private List<VoltDegree> voltList;
	private String nodeNo;
	private String orgType;
	private Date startDate;
	private Date endDate;
	private List<EnergyStat> esList;
	private String voltCode;
	private Date statDate;
	private String lineId;
	private String orgNo;
	private List<LoadStat> lsList;
	private long totalCount;
	private int start;
	private int limit;
	private Page<EnergyStat>  esPage;
	private Page<LoadStat>  lsPage;
			
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLineStatAnalysisManager(
			LineStatAnalysisManager lineStatAnalysisManager) {
		this.lineStatAnalysisManager = lineStatAnalysisManager;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<VoltDegree> getVoltList() {
		return voltList;
	}
	public void setVoltList(List<VoltDegree> voltList) {
		this.voltList = voltList;
	}
	public String getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
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
	
	public List<EnergyStat> getEsList() {
		return esList;
	}
	public void setEsList(List<EnergyStat> esList) {
		this.esList = esList;
	}
	
	public String getVoltCode() {
		return voltCode;
	}
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
	public List<LoadStat> getLsList() {
		return lsList;
	}
	public void setLsList(List<LoadStat> lsList) {
		this.lsList = lsList;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public Page<EnergyStat> getEsPage() {
		return esPage;
	}
	public void setEsPage(Page<EnergyStat> esPage) {
		this.esPage = esPage;
	}
	public Page<LoadStat> getLsPage() {
		return lsPage;
	}
	public void setLsPage(Page<LoadStat> lsPage) {
		this.lsPage = lsPage;
	}
	/**
	 * 根据供电单位查询
	 * @param NodeNo
	 * @return voltList
	 */
	public String queryVoltByNodeNo()
	{
		log.debug("action:queryVoltByNodeNo开始");
		this.setVoltList(lineStatAnalysisManager.queryVoltByNodeNo(type, nodeNo));
		log.debug("action:queryVoltByNodeNo结束");
		return SUCCESS;
	}
	/**
	 * 查询电量统计列表
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public String queryEnergyStat()
	{
		log.debug("action:queryEnergyStat开始");
		this.esPage=lineStatAnalysisManager.queryEnergyStat(type,nodeNo,voltCode,startDate,endDate,start,limit);
		this.esList=esPage.getResult();
		this.setTotalCount(esPage.getTotalCount());
		log.debug("action:queryEnergyStat结束");
		return SUCCESS;
	}
	/**
	 * 查询线路电量统计列表
	 * @param orgNo,lineId,statDate,voltCode
	 * @return esList
	 */
	public String queryLineEnergy()
	{
		log.debug("action:queryLineEnergy开始");
		this.esList=lineStatAnalysisManager.queryLineEnergy(orgNo,voltCode,lineId,statDate);
		log.debug("action:queryLineEnergy结束");
		return SUCCESS;
	}
	/**
	 * 查询负荷统计
	 * @param type,nodeNo,volt,startDate,endDate
	 * @return esList
	 */
	public String queryLoadStat()
	{
		log.debug("action:queryLoadStat开始");
		this.lsPage=lineStatAnalysisManager.queryLoadStat(type,nodeNo,voltCode,startDate,endDate,start,limit);
		this.lsList=lsPage.getResult();
		this.setTotalCount(lsPage.getTotalCount());
		log.debug("action:queryLoadStat结束");
		return SUCCESS;
	}
	

}
