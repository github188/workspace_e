
/**
 * 作者：
 * 修改人：ChunMingLi
 * 时间：2010/07/02
 */
 
var startDate;
var endDate;
var aw_Window;
function randomColor() {
	var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
	if (rand.length == 6) {
		return rand;
	} else {
		return randomColor();
	}
}

function getMultiXMLData(json) {
	var xmlData = "<graph caption='' xAxisName='' yAxisName='' showNames='1' decimalPrecision='0' formatNumberScale='0'>";

	var str = "";
	for (var i = 0; i < json.length; i++) {
		str += "<set name='" + json.get(i).get('orgName') + "' value='"
				+ json.get(i).get('total') + "' color='" + randomColor()    //每个单位颜色固定，?循环?
				+ "' />";
	}
	xmlData = xmlData + str + "</graph>";
	return xmlData;
}
//************************
//弹出窗口显示每日状态异常
function openWindowAmmeterStatus(orgNo,orgName,eventNo){
	
	var aw_cm = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dateIndex : 'orgName',
				align : 'center'
			},{
				header : '统计日期',
				dateIndex : 'statDate',
				align : 'center',
				renderer : function(v) {
					if(v != null){
					v = v.substring(0,10);
					}
					return '<div align = "center">'+ v + '</div>';	
				}
			}, {
				header : '事件名称',
				dateIndex : 'eventNo',
				align : 'center',
				renderer : function(v) {
					if (v == '0C') {
						v = "时间超差";
					} else if (v == '0D') {
						v = "电表故障";
					} else if (v == '1B') {
						v = "适度下降";
					} else if (v == '1C') {
						v = "电能表超差";
					} else if (v == '1D') {
						v = "电能表飞走";
					} else if (v == '1E') {
						v = "电能表停走";
					} else if (v == '1F') {
						v = "485抄表失败";
					}
					var html = '<span ext:qtitle="事件名称:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "center">' + html + '</div>';
				}
			},{
				header : '事件数',
				dateIndex : 'eventCnt',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="事件数:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openAmmeterH(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"'
								+ rec.get('statDate') + '\",\"'
								+ rec.get('orgType') + '\",\"'
								+ rec.get('eventNo') + "\");" + "'>" 
								+ s	+ "</a>";
					}
				}
			}]);

	var aw_store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'runman/queryAEventStatDBean!queryAEventStatWindow.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'aEventWindowList'
						}, [{
									name : 'orgName'
								}, {
									name : 'statDate'
								}, {
									name : 'eventNo'
								}, {
									name : 'eventCnt'
								}, {
									name : 'eventName'
								},{
									name : 'orgNo'
								},{
									name : 'orgType'
								}])
			});

	var aw_grid = new Ext.grid.GridPanel({
				id : 'aw_grid',
				border : true,
				region : 'center',
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				store : aw_store,
				cm : aw_cm
			});

	aw_Window = new Ext.Window({
				name : 'aw_Window',
				title : '电能表异常(每日统计)',
				width : 600,
				height : 300,
				layout : 'border',
				modal : true, // True 表示为当window显示时对其后面的一切内容进行遮罩,默认为false
				border : false,
				resizable : false,
				items : [aw_grid]
			});

	// 取Store
	aw_store.load({
				params : {
					startDate : Ext.getCmp("startDate").getValue().clearTime(),
					endDate : Ext.getCmp("endDate").getValue().clearTime(),
					orgNo : orgNo,
					eventNo : eventNo,
					orgName : orgName
				}
			});

	aw_Window.show();
}


Ext.onReady(function() {

	var arsDateForm1 = new Ext.FormPanel({
				labelAlign : 'right',
				labelWidth : 50,
				border : false,
				items : [{
							xtype : 'datefield',
							labelSeparator : ' ',
							allowBlank : false,
							format : 'Y-m-d',
							editable : false,
							width : 120,
							fieldLabel : '从',
							value : new Date().add(Date.DAY, -4),
							id : 'startDate',
							name : 'startDate'
						}]
			});
	var arsDateForm2 = new Ext.FormPanel({
				labelAlign : 'right',
				labelWidth : 50,
				border : false,
				items : [{
							xtype : 'datefield',
							labelSeparator : ' ',
							allowBlank : false,
							editable : false,
							format : 'Y-m-d',
							width : 120,
							fieldLabel : '到',
							value : new Date().add(Date.DAY, -1),
							id : 'endDate',
							name : 'endDate'
						}]
			});

	var arsQueryPanel = new Ext.Panel({ 		// 查询panel
		region : 'north',
		height : 40,
		bodyStyle : 'padding:7px 0px',
		layout : 'column',
		items : [{
					border : false,
					columnWidth : .25,
					layout:'form',
					items : [arsDateForm1]
				}, {
					border : false,
					columnWidth : .35,
					layout:'form',
					items : [arsDateForm2]
				}, {
					columnWidth : .2,
					border : false,
					items : [{
						xtype : 'button',
						text : '查询',//监听，点击，传值，参数，load
						listeners : {
							"click" : function() {
								startDate = Ext.getCmp("startDate").getValue().clearTime();
								endDate = Ext.getCmp("endDate").getValue().clearTime();
								arcStore.baseParams = {
									startDate : startDate,
									endDate : endDate
								};
								arcStore.load();
							}
						},
						width : 80
					}]
				}]
	});
	
        var asrCm = new Ext.grid.ColumnModel([
        	{
				header : '供电单位编号',
				dataIndex : 'orgNo',
				align : 'center',
				hidden:true
			},
				{
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center',
				renderer : function(v) {
					var html = '<span ext:qtitle="供电单位:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '异常合计',
				dataIndex : 'total',
				align : 'center'
			}, {
				header : '时间超差',
				dataIndex : 'sjcc',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="时间超差:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "0C" + "\");" + "'>" 
								+ s	+ "</a>";
					}
				}
			}, {
				header : '电表故障',
				dataIndex : 'dbgz',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="电表故障:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "0D" + "\");" + "'>" 
								+ s	+ "</a>";
					}
				}
			}, {
				header : '示度下降',
				dataIndex : 'sdxj',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="示度下降:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "1B" + "\");" + "'>" 
								+ s	+ "</a>";
					}
				}
			}, {
				header : '超差',
				dataIndex : 'dnbcc',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="超差:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "1C" + "\");" + "'>" + s
								+ "</a>";
					}
				}
			}, {
				header : '飞走',
				dataIndex : 'dnbfz',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="飞走:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "1D" + "\");" + "'>" + s
								+ "</a>";
					}
				}
			}, {
				header : '停走',
				dataIndex : 'dnbtz',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="停走:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "1E" + "\");" + "'>" + s
								+ "</a>";
					}
				}
			}, {
				header : '485抄表失败',
				dataIndex : 'cbsb',
				align : 'center',
				renderer : function(s, m, rec) {
					if (s == 0) {
						var html = '<span ext:qtitle="485抄表失败:" ext:qtip="' + s
								+ '">' + s + '</span>';
						return '<div align = "center">' + html + '</div>';
					} else {
						return "<a href='javascript:'onclick='openWindowAmmeterStatus(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "1F" + "\");" + "'>" 
								+ s	+ "</a>";
					}
				}
			}]);

	
    var arcStore = new Ext.data.Store({		//格式
		proxy : new Ext.data.HttpProxy({
					url : 'runman/queryAEventStatDBean!queryAEventStatDBean.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'aEventStatDBeanList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgNo'
						} ,{
							name : 'orgName'
						}, {
							name : 'total'
						}, {
							name : 'sjcc'
						}, {
							name : 'dbgz'
						}, {
							name : 'sdxj'
						}, {
							name : 'dnbcc'
						}, {
							name : 'dnbfz'
						}, {
							name : 'dnbtz'
						}, {
							name : 'cbsb'
						}])
	});

	arcStore.on("load", function(o, arr) {

				var xmlData = getMultiXMLData(arcStore.data);
				
				chartPanel.changeDataXML(xmlData);				
				startDate = Ext.getCmp("startDate").getValue().clearTime();
				endDate = Ext.getCmp("endDate").getValue().clearTime();
				//%%%%%%	fusionchart图自适应大小
//				var myChart = new FusionCharts("./fusionCharts/Column3D.swf",
//						"myChartId", "780", "240");
//				myChart.setDataXML(xmlData);
//				myChart.setTransparent(true);// 透明
//				myChart.render(chartPanel.getId());
				
			})
	arcStore.load({ // 默认查询三天区间，自动load数据
		params : {
			startDate : Ext.getCmp("startDate").getValue().clearTime(),
			endDate : Ext.getCmp("endDate").getValue().clearTime()
		}
	});
    
    var arsGrid = new Ext.grid.GridPanel({ // 下方panel的grid表格
		ds : arcStore,
		cm : asrCm,
		stripeRows : true,
		region : 'center',
		border : false,
		autoScroll : true,
		bbar : new Ext.ux.MyToolbar({
					store : arcStore,
					enableExpAll : true, // 导出全部
					expAllText : "全部",
					enableExpPage : true, // 导出当前页
					expPageText : "当前页"
				})
	});
	
//	arcStore.on('cellclick', function(grid, row, col) {
//				var asrCm = grid.getColumnModel();
//				var id = cm.getColumnId(col);
//				// alert(id);
//
//				if (id == 3) {
//					var rec = grid.getStore().getAt(row);
//					openWindowAmmeterStatus(rec);
//				}
//			});

	// var chartPanel = new Ext.Panel({ // 下方panel的chart直方图
	// border : false
	// region:'north',
	// height:240
	// });

	
	var chartPanel = new Ext.fc.FusionChart({ 
		wmode : 'transparent',
		backgroundColor : '000000',
		url : 'fusionCharts/Column3D.swf',
		// %%%%%%%% fusionchart自适应
		// url : 'fusionCharts/MSColumn3D.swf',
		// url : 'fusionCharts/StackedColumn3D.swf',
		// url : 'fusionCharts/ScrollColumn2D.swf',
		DataXML : ""
			// DataURL : 'YhData.xml'
	});
	
	

	var arsPanel1 = new Ext.Panel({ // 下方的panel1
		region : 'center',
		border : false,
		layout : 'border',
		items : [{						//背景色 属性bgcolor='000000'，是否用套panel
					labelAlign : 'right',
					region : 'north',
					layout : 'fit',
					height : 230,
					items : [chartPanel]//套层panel在chart外面底色才可消除????????????
				}, arsGrid]
	});

	var viewPanel = new Ext.Panel({
				layout : 'border',
				items : [arsQueryPanel, arsPanel1],
				border : false
			});
	
	renderModel(viewPanel, '电能表运行状态');

});


//下钻超查表
function openAmmeterH(orgNo, orgName,statDate,orgType, eventNo){
	ammeterRunStatus = 'ammeterRunStatus';
	staticAmmeterRunStatusStartDate = statDate;
	staticAmmeterRunStatusEndDate = statDate;
	staticAmmeterRunStatusFlag = eventNo;
	staticAmmeterRunStatusOrgType = orgType;
	staticAmmeterRunStatusOrgNo = orgNo;
	staticAmmeterRunStatusOrgName = orgName;
	aw_Window.close();
//	openTab("异常","./runMan/runStatusMan/ammeterH.jsp");
	openTab("异常查询","./runMan/abnormalHandle/exceptionSearch.jsp");
	
}
	
	