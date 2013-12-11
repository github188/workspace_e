Ext.onReady(function() {
	var reportManQueryPanel = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				border : true,
				items : [yearReportInfoPanel]
			});
	renderModel(reportManQueryPanel,'电能信息采集报表');
});