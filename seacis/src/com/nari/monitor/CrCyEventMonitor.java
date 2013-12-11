package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 催费控制服务事件
 */
public class CrCyEventMonitor {
	
	private EPStatement statement;
	
	public CrCyEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from CrCy";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}