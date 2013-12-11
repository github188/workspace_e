package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class PrePaidStatusAction extends BaseAction{

	//返回值
	private boolean success = true;
	
	private String  orgNo;           //供电单位编号
	private String  orgType;         //供电单位类型
	private String busiType;         //业务类别 
	private String  execDate;        //执行日期
	private Integer ctrlType;        //控制类型
	private Integer execStatus;      //执行状态
	private Integer statType;        //终端预购电执行情况统计类型
	//private String  appNo;           //购电单号
	//private String  consNo;          //用户编号
	private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private List<PrePaidCtrlExecStatBean> prePaidCtrlExecStatBeanList; //预付费控制执行统计结果
	private List<BuyExecStatusStatBean> buyExecStatusStatBeanList;     //预购电执行情况统计结果
	private List<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanList;       //终端预付费查询结果
	private List<UrgeFeeBean> urgeFeeBeanList;                 //预付费控制执行查询结果
	
	private IPrePaidStatusManager iPrePaidStatusManager;

	public void setiPrePaidStatusManager(IPrePaidStatusManager iPrePaidStatusManager) {
		this.iPrePaidStatusManager = iPrePaidStatusManager;
	}
	
    /**
     * 当日预付费控制执行统计
     * @return
     * @throws Exception
     */
	public String todayPerPaidCtrlExecStatistics()throws Exception{
		if(null==this.orgNo||"".equals(this.orgNo)
				||null==this.execDate||"".equals(this.execDate)
				||null==this.orgType||"".equals(this.orgType)){
			this.success=false;
			return SUCCESS;
		}
		try{
			this.prePaidCtrlExecStatBeanList=this.iPrePaidStatusManager.todayPerPaidCtrlExecStat(this.orgNo,this.execDate,this.orgType);
		    return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 当日购电执行情况统计
	 * @return
	 * @throws Exception
	 */
	public String todayBuyExecStatusStatistics()throws Exception{
		if(null==this.orgNo||"".equals(this.orgNo)
				||null==this.orgType||"".equals(this.orgType)
				||null==this.execDate||"".equals(this.execDate)){
			this.success=false;
			return SUCCESS;
		}
		try{
			this.buyExecStatusStatBeanList=this.iPrePaidStatusManager.todaybuyExecStatusStat(this.orgNo,this.orgType,this.execDate);
		    return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 终端预付费查询
	 * @return
	 * @throws Exception
	 */
	public String tmnlPrePaidQuery()throws Exception{
		if(null==this.orgNo||"".equals(this.orgNo)
				||null==this.orgType||"".equals(this.orgType)
				||null==this.execDate||"".equals(this.execDate)
				||null==this.statType){
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage = this.iPrePaidStatusManager
					.terminalPrePaidQuery(this.orgNo,this.orgType, this.execDate, this.statType,this.start, this.limit);
			this.tmnlPrePaidQueryBeanList=tmnlPrePaidQueryBeanPage.getResult();
			this.totalCount=tmnlPrePaidQueryBeanPage.getTotalCount();
		    return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 预付费控制执行查询
	 * @return
	 * @throws Exception
	 */
	public String buyCtrlExecQuery()throws Exception{
		if(null==this.orgNo||"".equals(this.orgNo)
				||null==this.orgType||"".equals(this.orgType)
				||null==this.execDate||"".equals(this.execDate)
				||null==this.ctrlType||null==this.execStatus){
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<UrgeFeeBean> urgeFeeBeanPage = this.iPrePaidStatusManager
					.prePaidCtrlExecQuery(this.orgNo, this.orgType, this.execDate, this.ctrlType,
							this.execStatus, this.start, this.limit);
			this.urgeFeeBeanList=urgeFeeBeanPage.getResult();
			this.totalCount=urgeFeeBeanPage.getTotalCount();
		    return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public List<PrePaidCtrlExecStatBean> getPrePaidCtrlExecStatBeanList() {
		return prePaidCtrlExecStatBeanList;
	}

	public void setPrePaidCtrlExecStatBeanList(
			List<PrePaidCtrlExecStatBean> prePaidCtrlExecStatBeanList) {
		this.prePaidCtrlExecStatBeanList = prePaidCtrlExecStatBeanList;
	}
	
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public List<BuyExecStatusStatBean> getBuyExecStatusStatBeanList() {
		return buyExecStatusStatBeanList;
	}

	public void setBuyExecStatusStatBeanList(
			List<BuyExecStatusStatBean> buyExecStatusStatBeanList) {
		this.buyExecStatusStatBeanList = buyExecStatusStatBeanList;
	}

	public String getExecDate() {
		return execDate;
	}

	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}

	public Integer getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(Integer execStatus) {
		this.execStatus = execStatus;
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

	public List<TmnlPrePaidQueryBean> getTmnlPrePaidQueryBeanList() {
		return tmnlPrePaidQueryBeanList;
	}

	public void setTmnlPrePaidQueryBeanList(
			List<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanList) {
		this.tmnlPrePaidQueryBeanList = tmnlPrePaidQueryBeanList;
	}

/*	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}*/

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(Integer ctrlType) {
		this.ctrlType = ctrlType;
	}

	public List<UrgeFeeBean> getUrgeFeeBeanList() {
		return urgeFeeBeanList;
	}

	public void setUrgeFeeBeanList(List<UrgeFeeBean> urgeFeeBeanList) {
		this.urgeFeeBeanList = urgeFeeBeanList;
	}

	public Integer getStatType() {
		return statType;
	}

	public void setStatType(Integer statType) {
		this.statType = statType;
	}
	
}
