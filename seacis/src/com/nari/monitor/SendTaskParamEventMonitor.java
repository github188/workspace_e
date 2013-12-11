package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发任务参数
 */
public class SendTaskParamEventMonitor {
	
	private EPStatement statement;
	
	public SendTaskParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendTaskParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}