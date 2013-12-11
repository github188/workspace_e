package com.nari.monitor.hb;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端参数
 */
public class HBSendTmnlParamOtherEventMonitor {
	
	private EPStatement statement;
	
	public HBSendTmnlParamOtherEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendTmnlParamOther";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}