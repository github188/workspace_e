package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class SendFrmTmnlParamEventMonitor {
private EPStatement statement;
	
	public SendFrmTmnlParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendFrmTmnlParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}
