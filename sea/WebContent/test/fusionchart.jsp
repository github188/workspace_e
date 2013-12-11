<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>fusionchart封装</title>

<link rel="stylesheet" type="text/css"	href="../ext3/resources/css/ext-all.css" />
<script type="text/javascript" src="../ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../ext3/ext-all.js"></script>
<script language="JavaScript" src="../fusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="../js/fusionChart.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	var keys = new Array('address','value');
	var colors = new Array('AFD8F8','BFD8F8','CFD8F8','DFD8F8','EFD8F8');
	var json = [{
		address:'合肥',
		value:10
	},{
		address:'六安',
		value:15
	},{
		address:'安庆',
		value:90
	},{
		address:'巢湖',
		value:5
	},{
		address:'淮南',
		value:35
	}];
	
	
	var keys1 = new Array("address","value","value1","value2","value3");
	var colors1 = new Array('AFD8F8','BFD8F8','CFD8F8','DFD8F8');
	var json1 = [{
		address:'合肥',
		value:10,
		value1:10,
		value2:10,
		value3:90
	},{
		address:'六安',
		value:15,
		value1:15,
		value2:10,
		value3:90
	},{
		address:'安庆',
		value:90,
		value1:90,
		value2:10,
		value3:90
	},{
		address:'巢湖',
		value:5,
		value1:5,
		value2:10,
		value3:90
	},{
		address:'淮南',
		value:35,
		value1:35,
		value2:10,
		value3:90
	}];

	//测试
	var xmlData1 = getSingleXMLData(json,keys,"测试",'供电单位','效率');
	var myChart1 = new FusionCharts("../fusionCharts/Column3D.swf","myChartId", "800", "300");
	myChart1.setDataXML(xmlData1);
	//myChart1.render("fusionchart");

	
	var xmlData = getMultiXMLData(json1,keys1,"测试",'供电单位','效率');
	var myChart = new FusionCharts("../fusionCharts/MSColumn3D.swf","myChartId", "800", "500");
	
	myChart.setDataXML(xmlData);
	myChart.render("fusionchart");
});
</script>
</head>
<body>
<div id="fusionchart"></div>
</body>
</html>