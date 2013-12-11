<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>电力用户用电信息采集系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="chrome=1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="./js/common.js"></script>
<script type="text/javascript" src="./ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="./ext3/ext-all.js"></script>
<script type="text/javascript" src="./ext3/src/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="login.js"></script>
<script type="text/javascript" src="./js/base64.js"></script>
<link rel="stylesheet" id="theme" type="text/css"
	href="./ext3/resources/css/ext-all.css" />
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>


<body>
<table align="center" valign="center">
	<tr>

		<td width="1000" height="640" background="images/login.png">

		
		<table>
			<tr>
				<td height="350" width="600" align="center" valign="bottom"> <!--  <a href="javascript:" onclick="open_win();">发布说明</a>--></td>
				<td></td>
				<td></td>
			</tr>

			<tr>
				<td rowspan="4">
				<!--  
					<marquee scrolldelay="300" onMouseOver=this.stop() onMouseOut=this.start() width="100%" height="80" direction="up"> 
						<iframe style="filter:chroma(color=#ffffff)" frameborder=0 marginheight=0 marginwidth=0 scrolling=no width="90%" height="1000" src=./history.html></iframe>
					</marquee>
				-->	
				</td>
				<td></td>
				<td align="left" height="17"><font size="3px" color="#FF0000"> <s:property
					value="errorMsg" /> </font></td>
			</tr>
			<form name="inputform" id="inputform">
			<tr>
				<td align="right" width="100">用 户 名：</td>
				<td align="left"><input type="text" name="staffNo_input" id="staffNo_input"
					value="" style="width: 200;" /></td>
			</tr>
			<tr>
				<td align="right">密 码：</td>
				<td><input type="password" name="password_input" id="password_input"
					value="" style="width: 200" /></td>
			</tr>
			</form>
			<tr>
				<td></td>
				<td align="right" 　valign="middle"><!--  <input type="checkbox"  > 保存密码</input>-->
				<form name="loginform" id="loginform" method="post"
			action="login.action" onsubmit="return login();">
				<input type="hidden" name="staffNo" id="staffNo" value="" />
				<input type="hidden" name="password" id="password" value="" />
<script type="text/javascript">
	document.onkeydown = function (evt){
		var evt = window.event?window.event:evt;
		if(evt.keyCode==13){
			login();
		}
	};

	function login() {
		var username = trim(document.inputform.staffNo_input.value);
		var userpass = trim(document.inputform.password_input.value);
		
		if (username == "" || username.length < 1) {
			alert("用户名不能为空");
			document.loginform.staffNo_input.select();
			document.loginform.staffNo_input.focus();
			return false;
		}
		if (userpass == "" || userpass.length < 1) {
			alert("用户密码不能为空");
			document.loginform.password_input.select();
			document.loginform.password_input.focus();
			return false;
		}

		document.loginform.staffNo.value = encode_base64(username);
		document.loginform.password.value = encode_base64(userpass);
		document.loginform.submit();
		//window.open("index.jsp","","fullscreen=1,menubar=0,toolbar=0,directories=0,location=0,	status=0,scrollbars=0")

	}
</script> <!-- <input type="submit" value=""
					Style="width:70;height:35;font-weight: bold; background-color: Transparent; background-image: url(./images/loginbutton.png);" />-->
<!--  <input id="loginbtn" name="loginbtn" type="image" src="./images/loginbutton.png" border="0" />-->
</form>
<img alt="" src="./images/loginbutton.png" onclick="javascript:login()"></img>

</td>
			</tr>
		</table>
		

		</td>

	</tr>
	<tr></tr>
</table>
</body>
</html>
