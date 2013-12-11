// 图片宽度
var width;

// 工单统计查询面板
var taskStatTop = new Ext.Panel({
			height : 35,
			region : 'north',
			border : false,
			width : 800,
			plain : true,
			items : [{
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				items : [{
					columnWidth : .3,
					layout : "table",
					layoutConfig : {
						columns : 2
					},
					baseCls : "x-plain",
					items : [{
						xtype : 'radiogroup',
						width : 200,
						style : "padding-left:22px",
						items : [{
									xtype : 'radio',
									checked : true,
									boxLabel : '分地区统计',
									name : 'fav-color',
									id : 'taskStatImage1',
									listeners : {
										check : function(checkbox, checked) {
											if (checked) {
												groupPanel.layout.setActiveItem(0);
											}
										}
									}
								}, {
									xtype : 'radio',
									boxLabel : '分类型统计',
									name : 'fav-color',
									id : 'taskStatImage2',
									listeners : {
										check : function(checkbox, checked) {
											if (checked) {
												groupPanel.layout.setActiveItem(1);
											}

										}

									}

								}]
					}]
				}, {
					columnWidth : .3,
					layout : "form",
					labelWidth : 70,
					baseCls : "x-plain",

					items : [{
								id : "taskState_dateFrom",
								xtype : "datefield",
								format : "Y-m-d",
								fieldLabel : "统计日期从",
								editable : false,
								value : new Date().add(Date.DAY,-6),
								labelStyle : "text-align:right;width:50;",
								labelSeparator : ""
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					labelWidth : 15,
					baseCls : "x-plain",
					items : [{
								id : "taskState_dateTo",
								xtype : "datefield",
								format : "Y-m-d",
								fieldLabel : "到",
								editable : false,
								value : new Date(),
								labelStyle : "text-align:right;width:50;",
								labelSeparator : ""
							}]
				}, {
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "查询",
						width : 50,
						handler : function() {
							var dateFrom = Ext.getCmp("taskState_dateFrom")
									.getValue();
							var dateTo = Ext.getCmp("taskState_dateTo")
									.getValue();
							if (dateFrom > dateTo) {
								Ext.MessageBox.alert("提示", "开始时间不能大于结束时间！");
								return;
							}
							dateFrom = dateFrom.format('Y-m-d');
							dateTo = dateTo.format('Y-m-d');

							// 加载选中的某个store并传入参数
							if (Ext.getCmp('taskStatImage1').checked) {
								areaStore.baseParams = {
									dateFrom : dateFrom,
									dateTo : dateTo
								}
								areaStore.load();
							} else if (Ext.getCmp('taskStatImage2').checked) {
								typeStore.baseParams = {
									dateFrom : dateFrom,
									dateTo : dateTo
								}
								typeStore.load();
							}
						}
					}]
				}]
			}]
		});
// ======================================================
//图片按地区统计图片 panel
var areaAutoChart = new Ext.fc.FusionChart({
			border : false,
			wmode : 'transparent',
			backgroundColor : 'ffffff',
			url : 'fusionCharts/Column2D.swf',
			DataXML : ""
		});

		//图片按类型统计图片 panel
var typeAutoChart = new Ext.fc.FusionChart({
			border : false,
			wmode : 'transparent',
			backgroundColor : 'ffffff',
			url : 'fusionCharts/Column2D.swf',
			DataXML : ""
		});

// 地区统计柱状图panel
var taskStatArea = new Ext.Panel({
			border : false,
			bodyBorder : false,
			layout : 'fit',
			// region : 'north',
			 height : 240,
			monitorResize : true
		});
// 类型统计柱状图panel
var taskStatType = new Ext.Panel({
			border : false,
			bodyBorder : false,
			layout : 'fit',
			// region : 'north',
			 height : 240,
			monitorResize : true
		});

// 类型图片面板
var taskStatTypeChart = new Ext.Panel({
			border : false,
			bodyBorder : false,
			region : 'north',
			style : "padding-left:20px",
			items : [taskStatType]
		});

// 地区图片面板
var taskStatAreaChart = new Ext.Panel({
			border : false,
			bodyBorder : false,
			region : 'north',
			style : "padding-left:20px",
			items : [taskStatArea]
		});


// 加载table数据
var areaStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/taskState!queryTaskStateByArea.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'taskStatList',
						totalProperty : 'totalCount'
					}, [{
								name : 'eventID'
							}, {
								name : 'orgNo'
							}, {
								name : 'orgName'
							}, {
								name : 'opTypeStateDBGD'
							}, {
								name : 'opTypeStateYXCLZ'
							}, {
								name : 'opTypeStateZCGD'
							}, {
								name : 'opTypeStateWBQR'
							}, {
								name : 'opTypeStateGQ'
							}, {
								name : 'opTypeStateBDCLZ'
							}, {
								name : 'opTypeStateQZGD'
							}, {
								name : 'total'
							}])
		});

// 定义一个table模型
var areaCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
					header : '序号',
					width : 40,
					align : 'center'
				}), {
			header : '工单编号',
			dataIndex : 'eventID',
			hidden : true,
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '供电单位编号',
			dataIndex : 'orgNo',
			hidden : true,
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '供电单位',
			dataIndex : 'orgName',
			width : 150,
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '待办工单',
			dataIndex : 'opTypeStateDBGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '营销处理中',
			dataIndex : 'opTypeStateYXCLZ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '正常归档',
			dataIndex : 'opTypeStateZCGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '误报确认',
			dataIndex : 'opTypeStateWBQR',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '挂起工单',
			dataIndex : 'opTypeStateGQ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '本地处理中',
			dataIndex : 'opTypeStateBDCLZ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '强制归档',
			dataIndex : 'opTypeStateQZGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '合计',
			dataIndex : 'total',
			sortable : true,
			renderer : function(s, m, rec) {
				return '<div align = "center">'
						+ "<a href='javascript:'onclick='openTaskDetailsByOrgNO(\""
						+ rec.get('orgNo') + "\");" + "'>" + s + '</a></div>';
			},
			align : 'center'
		}]);

// 获得table gird
var taskStatAreaGird = new Ext.grid.GridPanel({
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : areaCM,
			store : areaStore,
			bbar : new Ext.ux.MyToolbar({
						store : areaStore
					})
		});
// 获得table girdPanel
var taskStatAreaGirdPanel = new Ext.Panel({
			id : 'area_panel',
			autoScroll : true,
			region : 'center',
			border : false,
			items : [taskStatAreaGird]
		});

areaStore.on('load', function(o, arr) {

//			var width = taskStatArea.getWidth() - 25;

			// 地区统计chart
//			var taskStatChart1 = new FusionCharts("fusionCharts/Column2D.swf",
//					"taskStatImageId", width, "240");
//			taskStatChart1.setDataXML(createAreaChartsXmlDate(areaStore));
//			taskStatChart1.setTransparent(true);
//			taskStatChart1.render(taskStatArea.getId());
			var xmlData = createAreaChartsXmlDate(areaStore);
			areaAutoChart.changeDataXML(xmlData);
		});

// ================================

// 加载table数据
var typeStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/taskState!queryTaskStateByType.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'typeList',
						totalProperty : 'typeCount'
					}, [
							// {name : 'eventID'},
							// {name : 'orgNo'},
							{
						name : 'eventType'
					}, {
						name : 'opTypeStateDBGD'
					}, {
						name : 'opTypeStateYXCLZ'
					}, {
						name : 'opTypeStateZCGD'
					}, {
						name : 'opTypeStateWBQR'
					}, {
						name : 'opTypeStateGQ'
					}, {
						name : 'opTypeStateBDCLZ'
					}, {
						name : 'opTypeStateQZGD'
					}, {
						name : 'total'
					}])
		});

// 定义一个table模型
var typeCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
					header : '序号',
					width : 40,
					align : 'center'
				}), {
			header : '业务类型',
			dataIndex : 'eventType',
			width : 180,
			sortable : true,
			renderer : function(val) {
				return '<div align = "left">' + getEventType(val) + '</div>';
			},
			align : 'center'
		}, {
			header : '待办工单',
			dataIndex : 'opTypeStateDBGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '营销处理中',
			dataIndex : 'opTypeStateYXCLZ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '正常归档',
			dataIndex : 'opTypeStateZCGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '误报确认',
			dataIndex : 'opTypeStateWBQR',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '挂起工单',
			dataIndex : 'opTypeStateGQ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '本地处理中',
			dataIndex : 'opTypeStateBDCLZ',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '强制归档',
			dataIndex : 'opTypeStateQZGD',
			sortable : true,
			renderer : function(val) {
				return '<div align = "center">' + val + '</div>';
			},
			align : 'center'
		}, {
			header : '合计',
			dataIndex : 'total',
			sortable : true,
			renderer : function(s, m, rec) {
				return '<div align = "center">'
						+ "<a href='javascript:'onclick='openTaskDetailsByType(\""
						+ rec.get('eventType') + "\");" + "'>" + s
						+ '</a></div>';
			},
			align : 'center'
		}]);

// 获得table gird
var taskStatTypeGird = new Ext.grid.GridPanel({
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
//			region : 'center',
			cm : typeCM,
			store : typeStore,
			bbar : new Ext.ux.MyToolbar({
						store : typeStore
					})
		});
		


typeStore.on('load', function(o, arr) {
//			var width = taskStatType.getWidth() - 25;
//			// 类型统计 chart
//			var taskStatChart2 = new FusionCharts("fusionCharts/Column2D.swf",
//					"taskStatImageId", width, "240");
//			taskStatChart2.setDataXML(createTypeChartXmlDate(typeStore));
//			taskStatChart2.setTransparent(true);
//			taskStatChart2.render(taskStatType.getId());
	
			var xmlData = createTypeChartXmlDate(typeStore);
			typeAutoChart.changeDataXML(xmlData);
		});


// 类型统计图和表格排列面板
var typeChartGridPanel = new Ext.Panel({
			autoScroll : true,
			region : 'center',
			title : '类型工单统计',
			border : false,
			layout : 'border',
			items : [
			{
				xtype : 'panel',
				border : false,
				region : 'north',
				height : 240,
				layout : 'fit',
				items : [typeAutoChart]
			}, {
				xtype : 'panel',
				border : true,
				region : 'center',
				layout : 'fit',
				items : [taskStatTypeGird]
			}]
		});

// 地区统计图和表格排列面板
var areaChartGridPanel = new Ext.Panel({
			autoScroll : true,
			region : 'center',
			title : '地区工单统计',
			border : false,
			layout : 'border',
			items : [
					{
						xtype : 'panel',
						border : false,
						region : 'north',
						height : 240,
						layout : 'fit',
						items : [areaAutoChart]
					}, {
						xtype : 'panel',
						border : true,
						region : 'center',
						layout : 'fit',
						items : [taskStatAreaGird]
					}]
		});


// 合并地区和类型面板
var groupPanel = new Ext.Panel({
			region : "center",
			layout : "card",
			activeItem : 0,
			border : false,
			items : [areaChartGridPanel, typeChartGridPanel]
		});

// 工单统计页面
Ext.onReady(function() {
			// 工单统计页面panel
			var viewPanel = new Ext.Panel({
						autoScroll : true,
						layout : 'border',
						region : "center",
						border : false,
						items : [taskStatTop, groupPanel]
					});
			// 渲染
			renderModel(viewPanel, '工单统计');
			autoInitializeLoad();
		});
/**
 * 初始化数据，解决页面图片穿越问题
 * @param 
 */
function autoInitializeLoad() {
	var dateFrom = Ext.getCmp("taskState_dateFrom").getValue();
	var dateTo = Ext.getCmp("taskState_dateTo").getValue();
	dateFrom = dateFrom.format('Y-m-d');
	dateTo = dateTo.format('Y-m-d');

	// 加载选中的某个store并传入参数
		areaStore.baseParams = {
			dateFrom : dateFrom,
			dateTo : dateTo
		}
		areaStore.load();
		typeStore.baseParams = {
			dateFrom : dateFrom,
			dateTo : dateTo
		}
		typeStore.load();
}
// 获得地区Charts数据
function createAreaChartsXmlDate(areaStore) {
	var xmlDate = '<chart caption="" baseFont="宋体" baseFontSize="14" AxisName="工单数"  showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">';

	// 循环数组，设置lebel
	areaStore.each(function(obj) {
				xmlDate += '<set label="' + obj.get('orgName') + '" value="'
						+ obj.get('total') + '" />';
			});
	xmlDate += '</chart>';
	return xmlDate;

	/*
	 * var lebelStr = '<categories>';
	 * areaStore.each(function(obj){//循环数组，设置lebel lebelStr += '<category
	 * label="' + obj.get('orgName') +'" />'; });
	 * 
	 * lebelStr += '</categories>'; var valueStr1 = '<dataset
	 * seriesName="全部流量(M)"color="AFD8F8" showValues="0">';
	 * areaStore.each(function(obj){//循环数组，设置值 valueStr1 += '<set value="' +
	 * obj.get('allFlow') +'" />'; }); valueStr1 += '</dataset>';
	 */
}
// 获得类别Charts数据
function createTypeChartXmlDate(typeStore) {
	// 图型数据
	var xmlDate = '<chart caption="" baseFont="宋体" baseFontSize="14"  AxisName="工单数" showValues="0" decimals="0" formatNumberScale="0" useRoundEdges="1">';
	// 循环数组，设置lebel
	typeStore.each(function(obj) {
				var eventType = obj.get('eventType');
				xmlDate += '<set label="' + getEventType(eventType)
						+ '" value="' + obj.get('total') + '" />';
			});
	xmlDate += '</chart>';
	return xmlDate;
}
// 根据标识获得业务类型名称
function getEventType(val) {
	if (val == 1) {
		return "系统异常";
	} else if (val == 2) {
		return " 终端上报事件";
	} else if (val == 3) {
		return "主站分析终端故障";
	} else if (val == 4) {
		return "主站分析用电异常";
	} else if (val == 5) {
		return "主站分析数据异常";
	}
}

// 打开新的tab按照供电单位
function openTaskDetailsByOrgNO(orgNo) {
	staticTaskDetailsOrgNo = orgNo;
	staticTaskDetailsEventType = "";
	staticTaskDetailsDateFrom = Ext.getCmp("taskState_dateFrom").getValue()
			.format('Y-m-d');
	staticTaskDetailsDateTo = Ext.getCmp("taskState_dateTo").getValue()
			.format('Y-m-d');
	openTab("工单明细", "./qryStat/taskQuery/taskList.jsp");
}
// 打开新的tab按照类型
function openTaskDetailsByType(eventType) {
	staticTaskDetailsEventType = eventType;
	staticTaskDetailsOrgNo = "";
	staticTaskDetailsDateFrom = Ext.getCmp("taskState_dateFrom").getValue()
			.format('Y-m-d');
	staticTaskDetailsDateTo = Ext.getCmp("taskState_dateTo").getValue()
			.format('Y-m-d');
	openTab("工单明细", "./qryStat/taskQuery/taskList.jsp");
}