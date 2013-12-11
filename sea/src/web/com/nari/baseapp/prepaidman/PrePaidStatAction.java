/**
 * 创建人：姜海辉
 * 类描述：预付费执行统计Action层
 */
package com.nari.baseapp.prepaidman;

import java.util.List;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class PrePaidStatAction extends BaseAction{

	//返回值
	private boolean success = true;
	
	private String orgNo;           //供电单位编号
	private String orgType;        //供电单位类型
	private String startDate;       //开始日期
	private String endDate;        //结束日期
	private Integer execStatus;    //执行状态
	private List<PrePaidCtrlStatBean> prePaidCtrlStatBeanList;
	private List<PrePaidExecStatBean> prePaidExecStatBeanList;
	private List<TmnlPrePaidQueryBean> prePaidExecStatusQueryList;       //预付费执行统计明细
	private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
    private IPrePaidStatManager  iPrePaidStatManager;
    
	public void setiPrePaidStatManager(IPrePaidStatManager iPrePaidStatManager) {
		this.iPrePaidStatManager = iPrePaidStatManager;
	}

	/**
	 * 预付费控制类别统计及执行统计
	 * @return
	 * @throws Exception
	 */
	public String prePaidStat()throws Exception{
		if ((null == this.orgNo || "".equals(this.orgNo))
			||null == this.startDate || "".equals(this.startDate)
			||null == this.endDate || "".equals(this.endDate)) {
			this.success=false;
			return SUCCESS;
		}
		try{
			this.prePaidCtrlStatBeanList=this.iPrePaidStatManager.ctrlStat(this.orgNo,this.orgType,this.startDate,this.endDate);
			this.prePaidExecStatBeanList=this.iPrePaidStatManager.execStat(this.orgNo,this.orgType,this.startDate,this.endDate);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 预付费执行统计明细查询
	 * @return
	 * @throws Exception
	 */
	public String prePaidExecStatQuery()throws Exception{
		if (null == this.orgNo || "".equals(this.orgNo)
			||null == this.orgType || "".equals(this.orgType)
			||null == this.startDate || "".equals(this.startDate)
			||null == this.endDate || "".equals(this.endDate)||null==this.execStatus) {
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<TmnlPrePaidQueryBean> tmnlPrePaidQueryBeanPage = this.iPrePaidStatManager
					.execStatQuery(this.orgNo, this.orgType, this.startDate,
							this.endDate, this.execStatus, this.start,
							this.limit);
			this.prePaidExecStatusQueryList=tmnlPrePaidQueryBeanPage.getResult();
			this.totalCount=tmnlPrePaidQueryBeanPage.getTotalCount();
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

	public List<PrePaidCtrlStatBean> getPrePaidCtrlStatBeanList() {
		return prePaidCtrlStatBeanList;
	}
	public void setPrePaidCtrlStatBeanList(
			List<PrePaidCtrlStatBean> prePaidCtrlStatBeanList) {
		this.prePaidCtrlStatBeanList = prePaidCtrlStatBeanList;
	}

	public List<PrePaidExecStatBean> getPrePaidExecStatBeanList() {
		return prePaidExecStatBeanList;
	}

	public void setPrePaidExecStatBeanList(
			List<PrePaidExecStatBean> prePaidExecStatBeanList) {
		this.prePaidExecStatBeanList = prePaidExecStatBeanList;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(Integer execStatus) {
		this.execStatus = execStatus;
	}

	public List<TmnlPrePaidQueryBean> getPrePaidExecStatusQueryList() {
		return prePaidExecStatusQueryList;
	}

	public void setPrePaidExecStatusQueryList(
			List<TmnlPrePaidQueryBean> prePaidExecStatusQueryList) {
		this.prePaidExecStatusQueryList = prePaidExecStatusQueryList;
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

}
