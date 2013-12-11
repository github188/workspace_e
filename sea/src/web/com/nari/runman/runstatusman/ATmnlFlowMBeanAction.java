package com.nari.runman.runstatusman;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.statreport.ATmnlFlowMBean;
import com.nari.support.Page;


public class ATmnlFlowMBeanAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private ATmnlFlowMBeanManager aTmnlFlowMBeanManager;
	

	private List<ATmnlFlowMBean> aTmnlFlowMBeanList;
	private boolean success = true;
	private long totalCount;
	
	
	public Date searchDate;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;

	
	public long getStart() {
		return start;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
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
	
	
	public ATmnlFlowMBeanManager getaTmnlFlowMBeanManager() {
		return aTmnlFlowMBeanManager;
	}

	public void setaTmnlFlowMBeanManager(ATmnlFlowMBeanManager aTmnlFlowMBeanManager) {
		this.aTmnlFlowMBeanManager = aTmnlFlowMBeanManager;
	}

	public List<ATmnlFlowMBean> getaTmnlFlowMBeanList() {
		return aTmnlFlowMBeanList;
	}

	public void setaTmnlFlowMBeanList(List<ATmnlFlowMBean> aTmnlFlowMBeanList) {
		this.aTmnlFlowMBeanList = aTmnlFlowMBeanList;
	}
	

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String queryChannelMonitor() throws Exception {
		
		if("".equals(searchDate)||searchDate==null){
			searchDate=new Date();
		}
		try{
			String sDate = new SimpleDateFormat("yyyy-MM").format(searchDate);
			Page<ATmnlFlowMBean> channelPage = aTmnlFlowMBeanManager.findChannelMonitor(sDate,start,limit);
			aTmnlFlowMBeanList = channelPage.getResult();
			totalCount  = channelPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "success";
	}
}