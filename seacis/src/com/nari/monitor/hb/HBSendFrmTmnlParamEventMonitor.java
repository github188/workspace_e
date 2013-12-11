package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class HBSendFrmTmnlParamEventMonitor {
private EPStatement statement;
	
	public HBSendFrmTmnlParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBSendFrmTmnlParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}
