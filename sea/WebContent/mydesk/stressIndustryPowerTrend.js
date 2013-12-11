Ext.onReady(function(){
	var xmlData = '<chart palette="2" caption="重点行业周用电量趋势" xAxisName="" yAxisName="" showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">' +
	'<set label="沈阳供电公司" value="462" />' +
	'<set label="大连供电公司" value="857" />' +
	'<set label="鞍山供电公司" value="671" />' +
	'<set label="抚顺供电公司" value="494" />' +
	'</chart>';
	var chart = new FusionCharts("./fusionCharts/Column2D.swf", "ChartId", "330", "250");
	chart.setDataXML(xmlData);
	chart.setTransparent(true);
	chart.render('stressIndustryPowerTrend');
});