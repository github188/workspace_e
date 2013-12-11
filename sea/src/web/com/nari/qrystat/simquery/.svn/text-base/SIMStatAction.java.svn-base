package com.nari.qrystat.simquery;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public class SIMStatAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SIMStatAction.class);

	private SIMStatManager sIMStatManager;
	// 请求返回列表
	private List<SimInstall> simList;
	private List<SIMOverTraffic> simOverList;
	private List<SimDetailFlowInfoBean> simFlowList;
	private List<SimTrafficInfoBean> simTrafficList;
	private boolean success = true;
	private long totalCount;
	// 前台参数
	private String orgNo;
	private String simNo;
	private String runner;
	private String statusCode;
	private String chargeDateFrom;
	private String chargeDateTo;
	private Date dateFrom;
	private Date dateTo;
	
	private String trafficDateFrom;
	private String trafficDateTo;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;

	/**
	 * SIM卡安装情况统计
	 * @return
	 * @throws Exception
	 */
	public String querySIMInstallStat() throws Exception {
		try {

			Page<SimInstall> simPage = sIMStatManager.findSim(orgNo,simNo, start,
					limit, super.getPSysUser());
			simList = simPage.getResult();
			totalCount = simPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * SIM卡超流量统计
	 * @return
	 * @throws Exception
	 */
	public String querySIMOverTraffic() throws Exception {
		try {
			Page<SIMOverTraffic> simPage = sIMStatManager.findSimOverTraffic(
					orgNo, chargeDateFrom, chargeDateTo, "", start, limit);
			simOverList = simPage.getResult();
			totalCount = simPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * SIM卡流量信息查看
	 * @return
	 */
	public String querySimFlowInfo(){
		try {
			Page<SimDetailFlowInfoBean> simFlowPage = sIMStatManager.findSimDetailFlowInfo(
					simNo, dateFrom, dateTo, start, limit);
			simFlowList = simFlowPage.getResult();
			totalCount = simFlowPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	public String querySimTraffic() {
		try{
			PSysUser user = getPSysUser();
			Page<SimTrafficInfoBean> simTrafficPage = sIMStatManager.findSimTrafficInfo(user,trafficDateFrom, trafficDateTo,orgNo, start, limit);
			simTrafficList = simTrafficPage.getResult();
			totalCount = simTrafficPage.getTotalCount();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	// gettters and setters

	public List<SimInstall> getSimList() {
		return simList;
	}

	public void setSimList(List<SimInstall> simList) {
		this.simList = simList;
	}

	public List<SIMOverTraffic> getSimOverList() {
		return simOverList;
	}

	public void setSimOverList(List<SIMOverTraffic> simOverList) {
		this.simOverList = simOverList;
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

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getRunner() {
		return runner;
	}

	public void setRunner(String runner) {
		this.runner = runner;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getChargeDateFrom() {
		return chargeDateFrom;
	}

	public void setChargeDateFrom(String chargeDateFrom) {
		this.chargeDateFrom = chargeDateFrom;
	}

	public String getChargeDateTo() {
		return chargeDateTo;
	}

	public void setChargeDateTo(String chargeDateTo) {
		this.chargeDateTo = chargeDateTo;
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

	public void setsIMStatManager(SIMStatManager sIMStatManager) {
		this.sIMStatManager = sIMStatManager;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public List<SimDetailFlowInfoBean> getSimFlowList() {
		return simFlowList;
	}

	public void setSimFlowList(List<SimDetailFlowInfoBean> simFlowList) {
		this.simFlowList = simFlowList;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public List<SimTrafficInfoBean> getSimTrafficList() {
		return simTrafficList;
	}

	public void setSimTrafficList(List<SimTrafficInfoBean> simTrafficList) {
		this.simTrafficList = simTrafficList;
	}

	public String getTrafficDateFrom() {
		return trafficDateFrom;
	}

	public void setTrafficDateFrom(String trafficDateFrom) {
		this.trafficDateFrom = trafficDateFrom;
	}

	public String getTrafficDateTo() {
		return trafficDateTo;
	}

	public void setTrafficDateTo(String trafficDateTo) {
		this.trafficDateTo = trafficDateTo;
	}
}
