<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="mydesk/collectionCoverage.js"></script>
<title>采集覆盖率</title>
<script type="text/javascript">
var xmlData = '<chart palette="2" caption="采集覆盖率(%)" xAxisName="" yAxisName="" showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">' +
'<set label="沈阳供电公司" value="80" />' +
'<set label="大连供电公司" value="70" />' +
'<set label="鞍山供电公司" value="88" />' +
'<set label="抚顺供电公司" value="98" />' +
'</chart>';
var chart = new FusionCharts("./fusionCharts/Column3D.swf", "ChartId", "330", "250");
chart.setDataXML(xmlData);
chart.setTransparent(true);
chart.render('collectionCoverage');

</script>
</head>
<body>
<div id="collectionCoverage"></div>
</body>
</html>