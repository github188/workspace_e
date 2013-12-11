package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端参数
 */
public class SendTmnlParamEventMonitor {
	
	private EPStatement statement;
	
	public SendTmnlParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendTmnlParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}