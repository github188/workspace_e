package com.nari.advapp.vipconsman;

import java.util.Date;
import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.advapp.statanalyse.PowerFactor;
import com.nari.advapp.statanalyse.PowerFactorAnalysisManager;
import com.nari.advapp.statanalyse.VoltDegree;
import com.nari.support.Page;

import common.Logger;

public class PowerFactorAnalysisAction extends BaseAction {
	private Logger log=Logger.getLogger(PowerFactorAnalysisAction.class);
	private String consNo;
	private List<VoltDegree> voltList;
	private PowerFactorAnalysisManager powerFactorAnalysisManager;
	private Date startTime;
	private Date endTime;
	private Date pfaTime;
	private String voltCode;
	private List<PowerFactor> pfList;
	private boolean success=true;
	private String type;
	private long start;
	private long limit;
	private long totalCount;
	private Page <PowerFactor> pfPage;
	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	
	public List<VoltDegree> getVoltList() {
		return voltList;
	}

	public void setVoltList(List<VoltDegree> voltList) {
		this.voltList = voltList;
	}

//	public void setPowerFactorAnalysisManager(
//			PowerFactorAnalysisManager powerFactorAnalysisManager) {
//		this.powerFactorAnalysisManager = powerFactorAnalysisManager;
//	}
	

	public Date getStartTime() {
		return startTime;
	}

	public void setPowerFactorAnalysisManager(
			PowerFactorAnalysisManager powerFactorAnalysisManager) {
		this.powerFactorAnalysisManager = powerFactorAnalysisManager;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getVoltCode() {
		return voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	

	public List<PowerFactor> getPfList() {
		return pfList;
	}

	public void setPfList(List<PowerFactor> pfList) {
		this.pfList = pfList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Date getPfaTime() {
		return pfaTime;
	}

	public void setPfaTime(Date pfaTime) {
		this.pfaTime = pfaTime;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Page<PowerFactor> getPfPage() {
		return pfPage;
	}

	public void setPfPage(Page<PowerFactor> pfPage) {
		this.pfPage = pfPage;
	}

	/**
	 * 查询电压等级
	 * @parameters consNo
	 * @return volt
	 */
	public String queryVoltByConsNo()
	{
		log.debug("Action:queryVoltByConsNo start!");		
		voltList=powerFactorAnalysisManager.queryVoltByConsNo(consNo,type);		
		log.debug("Action:queryVoltByConsNo end!");
		return SUCCESS;
	}
	/**
	 * 查詢功率因素列表
	 * @param voltCode,consNo,startTime,endTime
	 * @return list
	 */
	public String queryPowerFactorList()
	{
		log.debug("Action:queryPowerFactorList start!");
		this.pfPage=powerFactorAnalysisManager.queryPowerFactorList(consNo,voltCode,pfaTime,type,start,limit);
		this.pfList=pfPage.getResult();
		this.setTotalCount(pfPage.getTotalCount());
		log.debug("Action:queryPowerFactorList end!");
		return SUCCESS;
	}

}
