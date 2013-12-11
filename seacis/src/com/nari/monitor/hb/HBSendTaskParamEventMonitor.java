package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发任务参数
 */
public class HBSendTaskParamEventMonitor {
	
	private EPStatement statement;
	
	public HBSendTaskParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBSendTaskParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}