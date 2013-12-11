package com.nari.action;

import org.apache.struts.actions.DispatchAction;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nari.service.SynDataForMarketingManager;
import com.nari.util.SpringStrutsPlugin;

public class BaseAction extends DispatchAction{
	
	private static ApplicationContext app = null;

	public Object getObjBean(String name) {
		if (app == null) {
			app = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());
		}
		return app.getBean(name);
	}

	private BeanFactory BeanFactory() {
		return SpringStrutsPlugin.getBeanFactory();
	}
	
	//得到SynDataForMarketingManager实例
	protected SynDataForMarketingManager getSynDataForMarketingManager() {
		return (SynDataForMarketingManager) BeanFactory().getBean("synDataForMarketingManager");
	}
}