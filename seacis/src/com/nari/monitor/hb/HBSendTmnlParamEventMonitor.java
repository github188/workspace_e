package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端参数
 */
public class HBSendTmnlParamEventMonitor {
	
	private EPStatement statement;
	
	public HBSendTmnlParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBSendTmnlParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}