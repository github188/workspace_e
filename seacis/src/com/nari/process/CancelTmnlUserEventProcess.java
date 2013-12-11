package com.nari.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EventBean;
import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.eventbean.BackFeedEvent;
import com.nari.global.Constant;
import com.nari.service.MarketTerminalConfigManager;

@SuppressWarnings("unused")
public class CancelTmnlUserEventProcess implements Runnable{
	private static final Log log = LogFactory.getLog(TriggerTestEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public CancelTmnlUserEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	private MarketTerminalConfigManager marketTerminalConfigManager;
	
	public MarketTerminalConfigManager getMarketTerminalConfigManager() {
		return marketTerminalConfigManager;
	}

	public void setMarketTerminalConfigManager(
			MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	public void run() {
		log.info("CancelTmnlUserEvent begin...");
		marketTerminalConfigManager = (MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		//终端资产编号
		String tmnlAssetNo =  (String) newEvents[0].get("tmnlAssetNo");
		//终端编号
		String tmnlId =  (String) newEvents[0].get("tmnlId");
		//终端变更标识
		String tmnlType =  (String) newEvents[0].get("tmnlType");
		//用户变更标识
		String consType =  (String) newEvents[0].get("consType");
		//用户编号
		String consNo =  (String) newEvents[0].get("consNo");
		//申请编号
		String appNo =  (String) newEvents[0].get("appNo");
		//台区标识
		String tgId =  (String) newEvents[0].get("tgId");
		//采集点编号
		String cpNo =(String) newEvents[0].get("cpNo");
		//计量点编号
		String mpNo = (String) newEvents[0].get("mpNo");
		
		//销户销终端和相关信息
		marketTerminalConfigManager.cancelUserTmnl(tmnlId, tmnlAssetNo, tmnlType, consType, consNo, appNo, tgId, cpNo);
		//TODO 删除缓存中的终端任务
		Constant.sendEvent(new BackFeedEvent(appNo, new Long(1)));
	}
}
