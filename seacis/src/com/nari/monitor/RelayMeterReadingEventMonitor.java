package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 中继抄表
 */
public class RelayMeterReadingEventMonitor {
	
	private EPStatement statement;
	
	public RelayMeterReadingEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from RelayMeterReading";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}