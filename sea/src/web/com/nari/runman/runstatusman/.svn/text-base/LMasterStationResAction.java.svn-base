package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.logofsys.LMasterStationResBean;

public class LMasterStationResAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	
	LMasterStationResManager lMasterStationResManager;
	
//	private boolean success = true;
	private List termTaskInfos ;

	public String queryTermTaskInfo(){
		termTaskInfos = lMasterStationResManager.getTermTaskInfo();
		return SUCCESS;
	}
//	
//	public boolean isSuccess() {
//		return success;
//	}
//
//	public void setSuccess(boolean success) {
//		this.success = success;
//	}
//
//	public void setlMasterStationResManager(
//			LMasterStationResManager lMasterStationResManager) {
//		this.lMasterStationResManager = lMasterStationResManager;
//	}
//
	public List getTermTaskInfos() {
		return termTaskInfos;
	}

	public void setTermTaskInfos(List termTaskInfos) {
		this.termTaskInfos = termTaskInfos;
	}


	
	
	
	private Date msDate;
	private boolean success = true;
	private List<LMasterStationResBean> wgjList;
	private List<LMasterStationResBean> cjjList;
	private List<LMasterStationResBean> webServiceList;
	private List<LMasterStationResBean> interfaceServiceList;
	private List<LMasterStationResBean> databaseList;

	public String queryLMasterStationRes() throws Exception {
		wgjList = lMasterStationResManager.findLMasterStationResWgj(msDate);
		cjjList = lMasterStationResManager.findLMasterStationResCjj(msDate);
		webServiceList = lMasterStationResManager
				.findLMasterStationResWeb(msDate);
		interfaceServiceList = lMasterStationResManager
				.findLMasterStationResJkfw(msDate);
		databaseList = lMasterStationResManager
				.findLMasterStationResSjk(msDate);
		return SUCCESS;
	}
	
//	getter and setter
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<LMasterStationResBean> getWgjList() {
		return wgjList;
	}

	public void setWgjList(List<LMasterStationResBean> wgjList) {
		this.wgjList = wgjList;
	}

	public List<LMasterStationResBean> getCjjList() {
		return cjjList;
	}

	public void setCjjList(List<LMasterStationResBean> cjjList) {
		this.cjjList = cjjList;
	}

	public List<LMasterStationResBean> getWebServiceList() {
		return webServiceList;
	}

	public void setWebServiceList(List<LMasterStationResBean> webServiceList) {
		this.webServiceList = webServiceList;
	}

	public List<LMasterStationResBean> getInterfaceServiceList() {
		return interfaceServiceList;
	}

	public void setInterfaceServiceList(
			List<LMasterStationResBean> interfaceServiceList) {
		this.interfaceServiceList = interfaceServiceList;
	}

	public List<LMasterStationResBean> getDatabaseList() {
		return databaseList;
	}

	public void setDatabaseList(List<LMasterStationResBean> databaseList) {
		this.databaseList = databaseList;
	}

	public LMasterStationResManager getlMasterStationResManager() {
		return lMasterStationResManager;
	}

	public void setlMasterStationResManager(
			LMasterStationResManager lMasterStationResManager) {
		this.lMasterStationResManager = lMasterStationResManager;
	}

	public Date getMsDate() {
		return msDate;
	}

	public void setMsDate(Date msDate) {
		this.msDate = msDate;
	}
}
