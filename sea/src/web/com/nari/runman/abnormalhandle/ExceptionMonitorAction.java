package com.nari.runman.abnormalhandle;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.basicdata.ElecExcepBean;
import com.nari.basicdata.FailureStatistic;
import com.nari.basicdata.LostMonitorBean;
import com.nari.basicdata.LostMonitorBean2;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.TerminalExceptionWorkBean;
import com.nari.privilige.PSysUser;

/**
 * 异常监测Action
 * 
 * @author wushaoxiong
 */

public class ExceptionMonitorAction extends BaseAction {


	


	private Logger logger = Logger.getLogger(this.getClass());
	// 要返回的值
	private List<TerminalExceptionWorkBean> tewb;
	
	private List<TerminalEventBean> telc;
	private List<LostMonitorBean> lmb;
	private List<LostMonitorBean2> lmb2;
	private List<FailureStatistic> failureStat;
    private List<ElecExcepBean> storeElecExcep;

	

	private boolean success = true;

	// 动态注入服务层
	private ExceptionMonitorManager exceptionMonitorManager;

	public void setExceptionMonitorManager(
			ExceptionMonitorManager exceptionMonitorManager) {
		this.exceptionMonitorManager = exceptionMonitorManager;
	}

	public String queryData() throws Exception {
		this.logger.debug("终端异常工况开始");
		PSysUser userInfo= (PSysUser)this.getSession().getAttribute("pSysUser");
		this.setTewb(this.exceptionMonitorManager.getTewbData(userInfo));
		this.logger.debug("终端异常工况结束");
		return SUCCESS;
	}
	public String queryTerminalEvent() throws Exception {
		this.logger.debug("终端事件开始");
		this.setTelc(this.exceptionMonitorManager.getTelcData());
		this.logger.debug("终端事件结束");
		return SUCCESS;
	}
	public String queryGstq() throws Exception{
		this.logger.debug("高损台区开始");
		this.setLmb(this.exceptionMonitorManager.getLmbData());
		this.logger.debug("高损台区结束");
		return SUCCESS;
	}
	public String queryGsxl() throws Exception{
		this.logger.debug("高损线路开始");
		this.setLmb2(this.exceptionMonitorManager.getLmb2Data());
		this.logger.debug("高损线路结束");
		return SUCCESS;
	}
    public String queryFailureStat() throws Exception{
    	this.logger.debug("终端装接调试开始");
    	this.setFailureStat(this.exceptionMonitorManager.getFailureStat());
    	this.logger.debug("终端装接调试结束");
    	return SUCCESS;
    }
    
    public String queryStoreElecExcep() throws Exception{
    	this.logger.debug("用电异常监测开始");
    	//用户
    	PSysUser userInfo = (PSysUser)this.getSession().getAttribute("pSysUser");
    	
    	this.setStoreElecExcep(this.exceptionMonitorManager.getStoreElecExcep(userInfo));
    	this.logger.debug("用电异常监测结束");
    	return SUCCESS;
    }
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<TerminalExceptionWorkBean> getTewb() {
		return tewb;
	}

	public void setTewb(List<TerminalExceptionWorkBean> tewb) {
		this.tewb = tewb;
	}

	public List<TerminalEventBean> getTelc() {
		return telc;
	}

	public void setTelc(List<TerminalEventBean> telc) {
		this.telc = telc;
	}

	public List<LostMonitorBean> getLmb() {
		return lmb;
	}

	public void setLmb(List<LostMonitorBean> lmb) {
		this.lmb = lmb;
	}

	public List<LostMonitorBean2> getLmb2() {
		return lmb2;
	}

	public void setLmb2(List<LostMonitorBean2> lmb2) {
		this.lmb2 = lmb2;
	}

	public ExceptionMonitorManager getExceptionMonitorManager() {
		return exceptionMonitorManager;
	}
	
	public List<ElecExcepBean> getStoreElecExcep() {
		return storeElecExcep;
	}

	public void setStoreElecExcep(List<ElecExcepBean> storeElecExcep) {
		this.storeElecExcep = storeElecExcep;
	}

	public List<FailureStatistic> getFailureStat() {
		return failureStat;
	}

	public void setFailureStat(List<FailureStatistic> failureStat) {
		this.failureStat = failureStat;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
