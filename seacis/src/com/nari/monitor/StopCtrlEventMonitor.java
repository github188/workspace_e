package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 营业报停服务事件
 */
public class StopCtrlEventMonitor {
	
	private EPStatement statement;
	
	public StopCtrlEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from StopCtrl";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}