package com.nari.monitor;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * 反馈营销
 */
public class BackFeedEventMonitor {
	
	private EPStatement statement;
	
	public BackFeedEventMonitor(EPAdministrator admin) {		
		String stmt = "select * from BackFeed";
		statement = admin.createEPL(stmt);
	}

	public void addListener(UpdateListener listener) {
		statement.addListener(listener);
	}
}