package com.nari.process;

import org.apache.log4j.Logger;
import com.espertech.esper.client.EventBean;
import com.nari.service.WsForMarketingManager;
import com.nari.webserviceclient.MarketingWSClient;

/**
 * 催费控制服务事件
 */
@SuppressWarnings("unused")
public class CrCyEventProcess implements Runnable{
	
	private MarketingWSClient marketingWSClient = null;
	private WsForMarketingManager wsForMarketingManager =  null;
	
	private static Logger logger = Logger.getLogger(CrCyEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public CrCyEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	public void run() {
		
	}
}