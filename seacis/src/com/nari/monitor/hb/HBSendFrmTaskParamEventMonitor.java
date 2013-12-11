package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class HBSendFrmTaskParamEventMonitor {
private EPStatement statement;
	
	public HBSendFrmTaskParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBSendFrmTaskParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}
