Ext.onReady(function() {
	var orgNoTitle;
    var confirmButton = new Ext.Button({
				text : '确定',
				handler : function(){
			     logQueryGridStore.baseParams={
			     	pubDate : Ext.getCmp("logTime").getRawValue()
			     }
				logQueryGridStore.load();
				}
			});
			
	var logTimePanel = new Ext.Panel({
				region : 'north',
				layout : 'table',
				height : 40,
				bodyStyle : 'padding:10px 0px',
				items : [{
							border : false,
							labelAlign : 'right',
							labelSeparator : '',
							labelWidth : 50,
							//padding : '0px 0px 0px 10px',
							layout : 'form',
							width : 170,
							items : [{
										id : 'logTime',
										xtype : 'datefield',
										editable : false,
										value : new Date(),
										fieldLabel : '选择时间',
										format : "Y-m-d"
									}]
						}, {
							border : false,
							labelAlign : 'right',
							labelSeparator : '',
							layout : 'form',
							items : [confirmButton]
						}]
			});
	//图片orgNo panel
	var logStasticsChart = new Ext.fc.FusionChart({
				border : false,
				wmode : 'transparent',
				backgroundColor : '000000',
				url : 'fusionCharts/ScrollStackedColumn2D.swf',
				DataXML : ""
			});
  var leftPanel=new Ext.Panel({
		border:false,
		region:"west",
		width:750,
		items:[logStasticsChart]
		});	
	var logQueryGridStore = new Ext.data.Store({
		url : './advapp/logReleaseQueryAction!queryLogStastics.action',
		method:'get',
		baseParams : {
			pubDate : Ext.getCmp("logTime").getRawValue()
		},
		reader : new Ext.data.JsonReader({
					root : 'logReleaseList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgNo'
						}, {
							name : 'orgName'
						}, {
							name : 'successNum'
						}, {
							name : 'failNum'
						}, {
							name : 'successScale'
						}])
	});
    logQueryGridStore.load();
	var logQueryCM = new Ext.grid.ColumnModel([{
						header : '供电编号',
						dataIndex : 'orgNo',
						hidden : true,
						sortable : true,
						renderer : '',
						align : 'center'
					}, {
						header : '供电名称',
						dataIndex : 'orgName',
						sortable : true,
						renderer : '',
						align : 'center'
					}, {
						header : '成功',
						dataIndex : 'successNum',
						sortable : true,
						align : 'center'
//						,
//						renderer : function(s, m, rec) {
//							return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\""
//									+ rec.get('terminalNo')
//									+ '\",\"'
//									+ rec.get('terminalFacturer')
//									+ '\",\"'
//									+ 1
//									+ '\",\"' + 1 + "\");" + "'>" + s + "</a>";
//						}
					}, {
						header : '失败',
						dataIndex : 'failNum',
						sortable : true,
						align : 'center'
//						,
//						renderer : function(s, m, rec) {
//							return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\""
//									+ rec.get('terminalNo')
//									+ '\",\"'
//									+ rec.get('terminalFacturer')
//									+ '\",\"'
//									+ 0
//									+ '\",\"' + 1 + "\" );" + "'>" + s + "</a>";
//						}
					}
					, {
						header : '成功率',
						dataIndex : 'successScale',
						sortable : true,
						align : 'center',
						renderer : function(s, m, rec) {
							var successCount = rec.get("successNum");
							var failCount = rec.get("failNum");
							if(!Ext.isEmpty(successCount)&&!Ext.isEmpty(failCount))
							{
							var scale = successCount
									/ (successCount + failCount);
							//	                                                  	  	scale = scale.toPrecision(4);
							scale = scale * 100;
							if (scale == 0) {
								return '<div align = "right">' + scale
										+ '</div>';
							}
							scale = scale + "";
							scale = scale.substring(0, 4);
							return '<div align = "right">' + scale + '% </div>';
						}
						}
					}
					]);
	//orgno图形列表面板
	var logChartGrid = new Ext.grid.GridPanel({
				region : 'center',
//				columnWidth:.5,
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				cm : logQueryCM,
				store : logQueryGridStore
			});
	logQueryGridStore.on("load", function(o, arr) {
				var xmlData = getOrgNoXMLData(logQueryGridStore.data);
				logStasticsChart.changeDataXML(xmlData);
				logDisplayPanel.setTitle(orgNoTitle);
			});
//	logQueryGridStore.load();
	//返回终端厂商面板
	var logDisplayPanel = new Ext.Panel({	
//		activeTab:0,
		region : 'center',
		title : ' ',
		layout : 'border',
		border:false,
		items:[leftPanel, logChartGrid]
	});	
	var comprePanel = new Ext.Panel({
	autoScroll : true,
	region : 'center',
	border : false,
	layout : 'border',
	viewConfig : {
					forceFit : true
				},
	items : [logTimePanel, logDisplayPanel]
});
	function getOrgNoXMLData(json) {
		//计算总成功数
		var allSuccessNum = 0;
		//计算总失败数
		var allFailNum = 0;
		//总成功率
		var allOrgNoUpscale=0;
		var xmlData = '<chart palette="2" caption="数据发布成功率" shownames="1" showvalues="0" '
				+ ' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

		var str = "<categories>";
		for (var i = 0; i < json.length; i++) {
			var name = json.get(i).get('orgName');
			if (name == null) {
				name = '未知';
			}
			str += "<category label='" + name + "' />";
		}
		str += "</categories>";

		var str2 = '<dataset seriesName="成功数" color="8BBA00" showValues="0">';
		for (var i = 0; i < json.length; i++) {
			str2 += "<set value='" + json.get(i).get('successNum') + "' />";
			//计算成功数
			allSuccessNum += json.get(i).get('successNum');
		}
		str2 += "</dataset>";

		var str1 = '<dataset seriesName="失败数" color="FF0000" showValues="0">';
		for (var i = 0; i < json.length; i++) {
			str1 += "<set value='" + json.get(i).get('failNum') + "'/>";
			//计算总离线数
			allFailNum += json.get(i).get('failNum');
		}
		str1 += "</dataset>";

		xmlData = xmlData + str + str1 + str2 + "</chart>";

		//计算成功率
		if(allSuccessNum==0&allFailNum==0)
		{
		allOrgNoUpscale=0
		}
		else{
			
		allOrgNoUpscale = allSuccessNum / (allSuccessNum + allFailNum);
		allOrgNoUpscale = allOrgNoUpscale * 100;
		allOrgNoUpscale = allOrgNoUpscale.toString();
		allOrgNoUpscale = allOrgNoUpscale.substring(0, 4);
		}

		orgNoTitle = "数据发布成功率  " + "<font color='red'>发布成功数[ " + allSuccessNum
				+ " ] 发布失败数[ " + allFailNum + " ] 发布成功率 [ " + allOrgNoUpscale
				+ "% ]</font>";
//		logDisplayPanel.setTitle(orgNoTitle);
		return xmlData;

	}
	renderModel(comprePanel, '数据发布结果统计及查询');

});
