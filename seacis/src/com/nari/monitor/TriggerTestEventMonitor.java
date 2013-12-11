package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 触发测试事件
 */
public class TriggerTestEventMonitor {
	
	private EPStatement statement;
	
	public TriggerTestEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from TriggerTest";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}