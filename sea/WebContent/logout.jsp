<%@ page language="java" pageEncoding="UTF-8"%>
<%
	session.invalidate();
	//session.removeAttribute("pSysUser");

	response.sendRedirect("login.jsp");
%>