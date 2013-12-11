package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.basicdata.BCommProtItemListId;
import com.nari.basicdata.ElecExcepBean;
import com.nari.basicdata.FailureStatistic;
import com.nari.basicdata.LostMonitorBean;
import com.nari.basicdata.LostMonitorBean2;
import com.nari.basicdata.TerminalEventBean;
import com.nari.basicdata.TerminalExceptionWorkBean;
import com.nari.privilige.PSysUser;

public interface ExceptionMonitorDao {
	public List<TerminalExceptionWorkBean> findTewbData(PSysUser userInfo);
	public List<TerminalEventBean> findTelcData();
	public List<LostMonitorBean> findLmbData();
	public List<LostMonitorBean2> findLmb2Data();
	public List<FailureStatistic> findFailureStat();
	public List<ElecExcepBean> findStoreElecExcep(PSysUser userInfo);
}
