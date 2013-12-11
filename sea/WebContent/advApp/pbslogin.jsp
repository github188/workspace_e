<%@page import="java.util.Date"%><%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import ="com.nari.privilige.PSysUser,
org.apache.log4j.Logger
"%>

<%
	Logger logger = Logger.getLogger(request.getClass());
	
	//通过action来判断是操作行为
	//1 --> 系统浏览
	//2 --> 系统生成
	String action = request.getParameter("action");
	String orgNo = request.getParameter("orgNo");
	String name = request.getParameter("name");
	
	String user = "";
	String userID = "";
	if(null == orgNo){
		//response.("用户登录信息错误，请联系管理员。");
		logger.debug("用户登录信息错误，请联系管理员。" );
	}else{
		logger.debug("用户登录的单位：" + orgNo );
		
		if(orgNo.length() < 5){
			logger.debug("用户登录单位信息错误。" );
		}else{
			orgNo = orgNo.substring(0,5);
			if(true == orgNo.equals("63101")){
				logger.debug("用户登录单位:省公司");
				user = "admin";
				userID = "184000005";
			}else if(true == orgNo.equals("63401")){
				logger.debug("用户登录单位:西宁");
				user = "xining";
				userID = "184000793";
			}else if(true == orgNo.equals("63402")){
				logger.debug("用户登录单位:海南");
				user = "hainan";
				userID = "184000790";
			}else if(true == orgNo.equals("63403")){
				logger.debug("用户登录单位:黄化");
				user = "huanghua";
				userID = "184000796";
			}else if(true == orgNo.equals("63404")){
				logger.debug("用户登录单位:海东");
				user = "haidong";
				userID = "184000794";
			}else if(true == orgNo.equals("63405")){
				logger.debug("用户登录单位:海北");
				user = "haibei";
				userID = "184000791";
			}else if(true == orgNo.equals("63406")){
				logger.debug("用户登录单位:海西");
				user = "haixi";
				userID = "184000792";
			}else if(true == orgNo.equals("63407")){
				logger.debug("用户登录单位:果洛");
				user = "guoluo";
				userID = "184000795";
			}else{
				logger.debug("用户登录单位:============ 错误 =============");
				user = "";		
			}
						
			if(false == user.isEmpty()){
				if(true == action.equals("1")){
					logger.debug("用户浏览关口模块数据浏览");
					response.sendRedirect("http://10.215.191.17:88/pbsweb/query/pages/BaseDataQuery/MeterDataQuery.jsf?userId=184000005&loginTime=" + Long.toString(System.currentTimeMillis()));				
				}else{
					logger.debug("用户浏览关口模块数据生成");
					response.sendRedirect("http://10.215.191.17:7008/pbs2000Web/pbsmanager.jsp?name=" + user +  "&=dname" + name);
				}
			}
		}	
	}
%>
