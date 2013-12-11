package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class SendFrmTaskParamEventMonitor {
private EPStatement statement;
	
	public SendFrmTaskParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendFrmTaskParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}
