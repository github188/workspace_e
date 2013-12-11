package com.nari.global;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.ApplicationContext;
import com.espertech.esper.client.EPServiceProvider;

/**
 * 跳转
 */
public class Constant {
	private static ApplicationContext ctx = null;
	private static EPServiceProvider epService;
	private static ThreadPoolExecutor pool ;

	public static ApplicationContext getCtx() {
		return ctx;
	}
	
	public static void setCtx(ApplicationContext ctx) {
		Constant.ctx = ctx;
	}
	
	public static Object getBean(String name) {
		return Constant.ctx .getBean(name);
	}

	public static EPServiceProvider getEpService() {
		return Constant.epService;
	}

	public static void setEpService(EPServiceProvider epService) {
		Constant.epService = epService;
	}
	
	public static void sendEvent(Object event) {
        epService.getEPRuntime().sendEvent(event);
    }

	public static ThreadPoolExecutor getPool() {
		return pool;
	}

	public static void setPool(ThreadPoolExecutor pool) {
		Constant.pool = pool;
	}
}