package com.nari.process;

import org.apache.log4j.Logger;
import com.espertech.esper.client.EventBean;
import com.nari.service.WsForMarketingManager;
import com.nari.webserviceclient.MarketingWSClient;

/**
 * 营业报停服务事件
 */
@SuppressWarnings("unused")
public class StopCtrlEventProcess implements Runnable{

	private MarketingWSClient marketingWSClient = null;
	private WsForMarketingManager wsForMarketingManager =  null;
	
	private static Logger logger = Logger.getLogger(StopCtrlEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public StopCtrlEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	public void run() {
		
	}
}