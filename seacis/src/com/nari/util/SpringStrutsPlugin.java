package com.nari.util;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringStrutsPlugin implements PlugIn {

	private static final Log logger = LogFactory.getLog(SpringStrutsPlugin.class);
	private static WebApplicationContext wac;
	public void destroy() {}

	public void init(ActionServlet actionsevlet, ModuleConfig mc)throws ServletException {
        try{
            wac = WebApplicationContextUtils.getRequiredWebApplicationContext(actionsevlet.getServletContext());
            logger.info("Spring has been setuped!");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Spring Plugin setup failed!",e);
            return;
        }
	}
	
    public static WebApplicationContext getBeanFactory(){
        return wac;
    }
}