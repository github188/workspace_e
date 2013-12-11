Ext.onReady(function(){
	var xmlData = '<chart palette="2" caption="Unit Sales" xAxisName="Month" yAxisName="Units" showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">' +
	'<set label="Jan" value="462" />' +
	'<set label="Feb" value="857" />' +
	'<set label="Mar" value="671" />' +
	'<set label="Apr" value="494" />' +
	'<set label="May" value="761" />' +
	'<set label="Jun" value="960" />' +
	'</chart>';
	var chart = new FusionCharts("./fusionCharts/Column2D.swf", "ChartId", "330", "250");
	chart.setDataXML(xmlData);
	chart.setTransparent(true);
	chart.render('collectionSuccess');		
})