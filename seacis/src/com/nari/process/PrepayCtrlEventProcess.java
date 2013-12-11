package com.nari.process;

import org.apache.log4j.Logger;
import com.espertech.esper.client.EventBean;
import com.nari.service.WsForMarketingManager;
import com.nari.webserviceclient.MarketingWSClient;

/**
 * 预购电控制服务事件
 */
@SuppressWarnings("unused")
public class PrepayCtrlEventProcess implements Runnable{

	private MarketingWSClient marketingWSClient = null;
	private WsForMarketingManager wsForMarketingManager =  null;
	
	private static Logger logger = Logger.getLogger(PrepayCtrlEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public PrepayCtrlEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	public void run() {
		
	}
}