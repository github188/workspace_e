package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 触发测试事件
 */
public class HBTriggerTestEventMonitor {
	
	private EPStatement statement;
	
	public HBTriggerTestEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBTriggerTest";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}