package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 预购电控制服务事件
 */
public class PrepayCtrlEventMonitor {
	
	private EPStatement statement;
	
	public PrepayCtrlEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from PrepayCtrl";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}