package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class CancelTmnlUserEventMonitor {
	
	private EPStatement statement;
	
	public CancelTmnlUserEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from CancelTmnlUser";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}
