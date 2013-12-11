package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 广播抄表
 */
public class BroadcastMeterReadingEventMonitor {
	
	private EPStatement statement;
	
	public BroadcastMeterReadingEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from BroadcastMeterReading";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}