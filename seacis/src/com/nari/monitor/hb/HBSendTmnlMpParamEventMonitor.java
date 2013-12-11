package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端测量点参数
 */
public class HBSendTmnlMpParamEventMonitor {
	
	private EPStatement statement;
	
	public HBSendTmnlMpParamEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from HBSendTmnlMpParam";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}