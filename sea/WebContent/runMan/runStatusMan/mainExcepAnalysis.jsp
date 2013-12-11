<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.nari.privilige.PSysUser" %>

<script type="text/javascript">

<%
String orgNo = ((PSysUser)request.getSession().getAttribute("pSysUser")).getOrgNo();
if(orgNo.length()==9) orgNo=orgNo.substring(0,7);
%>
var userInfoOrgNo = '<%= orgNo %>';
</script>

<script type="text/javascript" src="./runMan/runStatusMan/mainExcepAnalysis.js"></script>
