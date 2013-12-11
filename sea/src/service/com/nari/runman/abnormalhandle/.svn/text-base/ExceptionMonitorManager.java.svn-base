package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.basicdata.ElecExcepBean;
import com.nari.basicdata.FailureStatistic;
import com.nari.basicdata.LostMonitorBean;
import com.nari.basicdata.LostMonitorBean2;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.TerminalExceptionWorkBean;
import com.nari.privilige.PSysUser;

public interface ExceptionMonitorManager {
	public List<TerminalExceptionWorkBean> getTewbData(PSysUser userInfo) throws Exception;
	
	public List<TerminalEventBean> getTelcData() throws Exception;
	
	public List<LostMonitorBean> getLmbData() throws Exception;
	public List<LostMonitorBean2> getLmb2Data() throws Exception;
	public List<FailureStatistic> getFailureStat() throws Exception;
	
	public List<ElecExcepBean> getStoreElecExcep(PSysUser userInfo) throws Exception;
}
