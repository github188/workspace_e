package com.nari.runman.dutylog;

import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class PublicHibernateDaoImpl extends HibernateDaoSupport {
	public PublicHibernateDaoImpl(){
//		SessionFactory s = new AnnotationConfiguration().buildSessionFactory();
//		System.out.println(s);
//		setSessionFactory(s);
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		setSessionFactory((SessionFactory) wac.getBean("sessionFactory"));
	}
}
