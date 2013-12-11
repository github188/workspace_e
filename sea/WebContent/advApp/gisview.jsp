<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String http = "http://" + request.getServerName();
if(80 != request.getServerPort())
    http +=":" + Integer.toString(request.getServerPort());
http += "/IEIG";
%>

<html lang="en" dir="ltr">
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript">
function scaleSvg(num)
{
   var root;
   root=svg.getSVGDocument().documentElement;
   svg.style.width=root.attributes.getNamedItem("width").nodeValue * num;
   svg.style.height=root.attributes.getNamedItem("height").nodeValue * num;
   root.currentScale =  num;
   
}
</script>
</head>
<body>
<embed id="svgitem" name="svg" width="100%" height="100%"
	src="<%  String file = request.getParameter("src");  if((null == file) || (file.isEmpty()))  out.write(http + "/svgview/graphics/main.svg");   else out.write(http + "/svgview/graphics/"+file); %>"
	type="image/svg+xml"
	pluginspage="http://www.adobe.com/svg/viewer/install/">
</body>
</html>
