package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 中继抄表
 */
public class HBRelayMeterReadingEventMonitor {
	
	private EPStatement statement;
	
	public HBRelayMeterReadingEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBRelayMeterReading";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}