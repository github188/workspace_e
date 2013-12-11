Ext.onReady(function() {
	// var chart = new FusionCharts("./fusionCharts/ScrollColumn2D.swf",
	// "ChartId", "330", "300");
	var commChartPanel = new Ext.fc.FusionChart({
				border : false,
				title : '',
				wmode : 'transparent',
				backgroundColor : '000000',
				url : 'fusionCharts/ScrollColumn2D.swf',
				DataXML : ""
			});

	var commChartPanelCon = new Ext.Panel({
				layout : 'fit',
				items : [commChartPanel]
			});

	var commXmlData = '<chart palette="3" ' + 'caption=""' + ' numberprefix=""'
			+ ' xaxisName=""' + ' useRoundEdges="1"' + ' showValues="0"'
			+ ' labelDisplay="Rotate"' + ' slantLabels="1"'
			+ ' legendBorderAlpha="0"' + ' baseFont="宋体" '
			+ ' baseFontSize="12">';
	var category = '<categories>';
	var dataset = '<dataset seriesname="">';
	Ext.Ajax.request({
				url : 'mydesk/mydesk!queryCommFlow.action',
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (result == null)
						return true;
					var commFlowList = result.commFlowList;

					for (var i = 0; i < commFlowList.length; i++) {
						category += '<category label="'
								+ commFlowList[i].flow_date + '" />';
						dataset += '<set value="' + commFlowList[i].flow
								+ '" /> ';
					}
					category += '</categories>';
					dataset += '</dataset>';
					commXmlData = commXmlData + category + dataset + '</chart>';

					// chart.setDataXML(xmlData);
					// chart.setTransparent(true);

					renderModel(commChartPanelCon, '5');
					commChartPanel.changeDataXML(commXmlData);
					// chart.render("commTraffic");
				},
				failure : function() {
					Ext.MessageBox.alert("提示", "失败");
					return;
				}
			});
})