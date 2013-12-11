package com.nari.baseapp.datagathorman.impl;




import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
/***
 * 在spring+struts 2 中通过直接new的方式来进行初始化
 * 在dataSource或者数据库session放置到ThreadLocal中
 * 在多数框架中都有实现
 * @author huangxuan
 * ***/
public class LeftTreeDaoImpl extends JdbcBaseDAOImpl  {

	/***
	 *
	 * **/
	public LeftTreeDaoImpl(){
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
	this.setDataSource((DataSource) wac.getBean("dataSource"));
	}

	
	
	
}
