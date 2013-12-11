package com.nari.advapp.vipconsman;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;

public class VipConsMonitorAction extends BaseAction{
	//返回值
	private boolean success = true;
	private Short queryType;                //查询类型
	private String queryDay;                 //日期
	private String queryMonth;               //月份
	private String queryYear;                //年份
	private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private List<VipConsMonitorDto> vipConsMonitorDtoList; 
	
	private VipConsMonitorManager vipConsMonitorManager;
	
	public void setVipConsMonitorManager(VipConsMonitorManager vipConsMonitorManager) {
		this.vipConsMonitorManager = vipConsMonitorManager;
	}
	
	public String queryReqRatio() throws Exception{
		if(null==this.queryType){
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<VipConsMonitorDto> vipConsMonitorDtoPage=this.vipConsMonitorManager.queryVipConsReqRatio(this.queryType, this.queryDay, this.queryMonth, this.queryYear, this.start, this.limit);
			this.vipConsMonitorDtoList=vipConsMonitorDtoPage.getResult();
			this.totalCount=vipConsMonitorDtoPage.getTotalCount();
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
	public Short getQueryType() {
		return queryType;
	}
	public void setQueryType(Short queryType) {
		this.queryType = queryType;
	}
	public String getQueryDay() {
		return queryDay;
	}

	public void setQueryDay(String queryDay) {
		this.queryDay = queryDay;
	}

	public String getQueryMonth() {
		return queryMonth;
	}
	public void setQueryMonth(String queryMonth) {
		this.queryMonth = queryMonth;
	}
	public String getQueryYear() {
		return queryYear;
	}
	public void setQueryYear(String queryYear) {
		this.queryYear = queryYear;
	}

	public long getStart() {
		return start;
	}
	
	public long getLimit() {
		return limit;
	}

	public void setStart(long start) {
		this.start = start;
	}

	
	public void setLimit(long limit) {
		this.limit = limit;
	}


	public List<VipConsMonitorDto> getVipConsMonitorDtoList() {
		return vipConsMonitorDtoList;
	}

	public void setVipConsMonitorDtoList(
			List<VipConsMonitorDto> vipConsMonitorDtoList) {
		this.vipConsMonitorDtoList = vipConsMonitorDtoList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
}
