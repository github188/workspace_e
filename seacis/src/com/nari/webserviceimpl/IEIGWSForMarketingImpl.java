package com.nari.webserviceimpl;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.eventbean.CrCyEvent;
import com.nari.eventbean.PrepayCtrlEvent;
import com.nari.eventbean.StopCtrlEvent;
import com.nari.eventbean.TriggerTestEvent;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.service.SynDataForMarketingManager;
import com.nari.service.WsForMarketingManager;
import com.nari.util.StringUtil;
import com.nari.util.XMLSwitchUtil;
import com.nari.webservice.IEIGWSForMarketing;
import com.nari.webserviceclient.MarketingWSClient;
import com.nari.webserviceconfig.InterfaceLogWriter;


@SuppressWarnings("unused")
public class IEIGWSForMarketingImpl implements IEIGWSForMarketing {

	private static Logger logger = Logger
			.getLogger(IEIGWSForMarketingImpl.class);
	private InterfaceLogWriter interfaceLogWriter;
	private MarketTerminalConfigManager marketTerminalConfigManager;

	public void setMarketTerminalConfigManager(
			MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}
	private SynDataForMarketingManager synDataForMarketingManager;
	
	public SynDataForMarketingManager getSynDataForMarketingManager() {
		return synDataForMarketingManager;
	}

	public void setSynDataForMarketingManager(
			SynDataForMarketingManager synDataForMarketingManager) {
		this.synDataForMarketingManager = synDataForMarketingManager;
	}



	@Override
	public String WS_CONS_OVERDUE() {
		logger.debug("IEIGWSForMarketingImpl.WS_CONS_OVERDUE input xml:" + "空");
		// 返回值和判断标志
		String returnValue = "";
		boolean flag = false;
		int errCode = 0;// 返回的默认值
		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil();
		System.out.println("欠费用户信息同步事件逻辑处理开始....");
		logger.debug("IEIGWSForMarketingImpl.WS_SynDataProcess 欠费用户信息同步事件逻辑处理开始");
		SynDataForMarketingManager synDataForMarketingManager = null;
     	this.synDataForMarketingManager = (SynDataForMarketingManager) Constant.getCtx().getBean("synDataForMarketingManager");
	    flag = this.synDataForMarketingManager.synDueDataByService();
		System.out.println("欠费用户信息同步事件逻辑处理结束....");
		logger.debug("IEIGWSForMarketingImpl.WS_CONS_OVERDUE 欠费用户信息同步事件逻辑处理结束");
		if (flag) {
			errCode = 1;// 表示执行成功
		} else {
			errCode = -1;// 表示执行失败
		}
		returnValue = xmlSwitch.genXMLFromErrCode(errCode);
		logger.debug("IEIGWSForMarketingImpl.WS_CONS_OVERDUE output xml:"
				+ returnValue);
		return "hello world";
	}

	@Override
	public String WS_CONS_PAY(String xmlInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}