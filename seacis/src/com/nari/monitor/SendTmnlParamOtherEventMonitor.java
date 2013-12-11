package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 下发终端参数
 */
public class SendTmnlParamOtherEventMonitor {
	
	private EPStatement statement;
	
	public SendTmnlParamOtherEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from SendTmnlParamOther";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}