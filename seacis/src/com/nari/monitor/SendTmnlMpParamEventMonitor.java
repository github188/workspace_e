package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端测量点参数
 */
public class SendTmnlMpParamEventMonitor {
	
	private EPStatement statement;
	
	public SendTmnlMpParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendTmnlMpParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}