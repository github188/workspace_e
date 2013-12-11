
//弹出窗口变量
var dm_Window;

var arrayCm = [{
			id : 'orgName',
			header : '供电单位',
			dataIndex : 'orgName',
			align : 'center',
			width:90,
			sortable : true
		}];
var arrayStore = new Array('orgName');
/*--------------------------------------------------------------------------------*/
var monitorStartDate = new Ext.form.DateField({
	name : "monitorStartDate",
	format : 'Y-m-d',
	allowBlank : false,
	editable : false,
	emptyText : '请选择日期 ...',
	fieldLabel : "从",
	width : "110",
	value : new Date().add(Date.DAY, -6)
});
var monitorEndDate = new Ext.form.DateField({
	name : "monitorEndDate",
	format : 'Y-m-d',
	allowBlank : false,
	editable : false,
	emptyText : '请选择日期 ...',
	fieldLabel : "到",
	width : "110",
	value : new Date()
});
monitorEndDate.on('change',function(df,newValue,oldValue){
	if(newValue < monitorStartDate.getValue()){
		monitorEndDate.setValue(oldValue);
		Ext.Msg.alert('提示','结束日期不能小于开始日期');	
	}
});
var monitorDateField = {
	layout : 'column',
	border : false,
	width:320,
	style : {
		marginTop : '0px',
		marginBottom : '10px',
		marginLeft : '2px',
		marginRight : '10px'
	},
	items : [{
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [monitorStartDate]
			}, {
				layout : 'form',
				border : false,
				labelWidth : 20,
				width : '140',
				items : [monitorEndDate]
			}]
};

var monitorQueryPanel = new Ext.Panel({
			labelAlign : 'right',
			region : 'north',
			frame : false,
			style : {
				marginTop : '10px',
				marginBottom : '1px',
				marginLeft : '10px'
			},
			height : 40,
			layout : 'column',
			border : false,
			items : [{
						columnWidth:.45,
						style : {
							marginTop : '1px',
							marginBottom : '1px',
							marginLeft : '1px'
						},
						border : false,
						items : monitorDateField
					}, {
						columnWidth:.2,
						border : false,
						style : {
							marginTop : '1px',
							marginBottom : '1px'
						},
						items : [{
									xtype : 'button',
									text : '查询',
									width : 50,
									handler:function(){
										genData();
									}
								}]
					}]
		});

//FusionChart--------------------------------------------------------------------------------
var dm_keys = new Array('types','value');
var dm_colors = new Array('AFD8F8','F6BD0F','8BBA00');
var levelArray = [];var factoryArray = [];
var dm_myChart1,dm_myChart2;
function genFusionChart(){
	var levelXmlData = getSingleXMLData(levelArray,dm_keys,'','','',dm_colors);
	var factoryXmlData = getSingleXMLData(factoryArray,dm_keys,"",'','');
	dm_myChart1 = new FusionCharts("./fusionCharts/Pie3D.swf", "myChartId1", "430",
		"210");
	dm_myChart1.setDataXML(levelXmlData);
	dm_myChart1.setTransparent(true);
	dm_myChart2 = new FusionCharts("./fusionCharts/Pie3D.swf", "myChartId2", "430",
		"210");
	dm_myChart2.setDataXML(factoryXmlData);
	dm_myChart2.setTransparent(true);

	if (chartPanel1 != null)
		dm_myChart1.render(chartPanel1.getId());
	if (chartPanel2 != null)
		dm_myChart2.render(chartPanel2.getId());
}

var chartPanel1 = new Ext.Panel({
			border : false,
			layout:'fit',
			frame : false,
			height:210
		});
var chartPanel2 = new Ext.Panel({
			height:210,
			layout:'fit',
			border : false,
			frame : false
		});
//grid-----------------------------------------------------------------------------------
var cm1 = new Ext.grid.ColumnModel([{
			id : 'orgName',
			header : '供电单位',
			dataIndex : 'orgName',
			align : 'center',
			sortable : true
		},{
			header:'单位编码',
			dataIndex : 'orgNo',
			align:'center',
			sortable:true,
			hidden:true
		}, {
			header : '严重',
			dataIndex : 'serisEvents',
			align : 'center',
			sortable : true,
			renderer : function(s, m, rec) {
				if(s==null || s=='') return "";
//				return "<a href='javascript:' onclick='devExcepWindowShow(\"03\")'>"+s+"</a>";
				return "<a href='javascript:'onclick='openWindowSeriousLevel(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "03" + "\");" + "'>" 
								+ s	+ "</a>";
			}
		}, {
			header : '次要',
			dataIndex : 'minorEvents',
			align : 'center',
			sortable : true,
			renderer : function(s, m, rec) {
				if(s==null || s=='') return "";
//				return "<a href='javascript:' onclick='devExcepWindowShow(\"02\")'>"+s+"</a>";
				return "<a href='javascript:'onclick='openWindowSeriousLevel(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "02" + "\");" + "'>" 
								+ s	+ "</a>";
			}
		}, {
			header : '一般',
			dataIndex : 'generEvents',
			align : 'center',
			sortable : true,
			renderer : function(s, m, rec) {
				if(s==null || s=='') return "";
//				return "<a href='javascript:' onclick='devExcepWindowShow(\"01\")'>"+s+"</a>";
				return "<a href='javascript:'onclick='openWindowSeriousLevel(\""
								+ rec.get('orgNo') + '\",\"'
								+ rec.get('orgName') + '\",\"' + "01" + "\");" + "'>" 
								+ s	+ "</a>";
			}
		}, {
			header : '全部',
			dataIndex : 'qb',
			align : 'center',
			sortable : true,
			renderer:function(s,m,rec){
				if(rec!=null){
					var serisE = new Number(rec.get('serisEvents'));
					var minorE = new Number(rec.get('minorEvents'));
					var generE = new Number(rec.get('generEvents'));
					return '<div align = "center">' + (serisE+minorE+generE) + '</div>';
//					return "<a href='javascript:' onclick='devExcepWindowShow(\"04\")'>"+(serisE+minorE+generE)+"</a>";
				}
				else return '';
			}
		}]);
var gridStore1 = new Ext.data.JsonStore({
	proxy : new Ext.data.MemoryProxy(),
	fields : ['orgName','orgNo','serisEvents','minorEvents','generEvents'],
	root : "serisLevelList"
});
var gridStore2;

var grid1 = new Ext.grid.GridPanel({
			title:'严重级别',
			store : gridStore1,
			border : false,
			autoWidth : true,
			cm : cm1,
			stripeRows : true,
			autoScroll : true,
			viewConfig : {
				forceFit:false
			}
		});

var monitorChart = new Ext.Panel({
			layout : 'column',
			title : '设备监测情况',
			border : false,
			region:'center',
			items : [{
						xtype : 'panel',
						border : true,
						columnWidth : .5,
						layout:'fit',
						items : [chartPanel1]
					}, {
						xtype : 'panel',
						columnWidth : .5,
						border : true,
						layout:'fit',
						items : [chartPanel2]
					}]
		});
var queryPanel1 = new Ext.Panel({
	border:false,
	layout:'fit',
	region:'north',
	height:40,
	items:[monitorQueryPanel]
});		
var monitorTop = new Ext.Panel({
	layout:'border',
	border:false,
	region:'north',
	height:270,
	items:[queryPanel1,monitorChart]
});


//弹出窗口显示统计每日严重级别
function openWindowSeriousLevel(orgNo,orgName,levelNo){
	
	var dm_cm = new Ext.grid.ColumnModel([{
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
				header : '严重级别',
				dateIndex : 'levelNo',
				align : 'center',
				renderer : function(v) {
					if (v == '03') {
						v = "严重";
					} else if (v == '02') {
						v = "次要";
					} else if (v == '01') {
						v = "一般";
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
						return "<a href='javascript:' onclick='devExcepWindowShow(\"" 
						+ rec.get('levelNo') + '\",\"'
						+ rec.get('statDate') + '\",\"'
						+ "\")'>"+s+"</a>";
					}
				}
			}]);

	var dm_store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'baseapp/deviceMonitor!queryDaySeriousLevel.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'dmWindowList'
						}, [{
									name : 'orgName'
								}, {
									name : 'statDate'
								}, {
									name : 'levelNo'
								}, {
									name : 'eventCnt'
								},{
									name : 'orgNo'
								},{
									name : 'orgType'
								}])
			});

	var dm_grid = new Ext.grid.GridPanel({
				id : 'dm_grid',
				border : true,
				region : 'center',
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				store : dm_store,
				cm : dm_cm
			});

	dm_Window = new Ext.Window({
				name : 'dm_Window',
				title : '严重级别(每日统计)',
				width : 600,
				height : 300,
				layout : 'border',
				modal : true, // True 表示为当window显示时对其后面的一切内容进行遮罩,默认为false
				border : false,
				resizable : false,
				items : [dm_grid]
			});

	// 取Store
	dm_store.load({
				params : {
					monitorStartDate : monitorStartDate.getValue().format('Y-m-d'),
					monitorEndDate : monitorEndDate.getValue().format('Y-m-d'),
					orgNo : orgNo,
					levelNo : levelNo,
					orgName : orgName
				}
			});

	dm_Window.show();
}


//根据严重程度来查看供电单位出故障的设备
function devExcepWindowShow(value,startDate){
	excep_seriousLevel = value;	//严重程度
	var record =  grid1.getSelectionModel().getSelected();
	if(record!=null){
    	excep_compName = record.get('orgName');	//供电单位
    	excep_orgNo = record.get('orgNo');	//供电单位编码
    }
    excep_startDate = startDate.substring(0,10);
    excep_endDate = startDate.substring(0,10);
//    excep_startDate = monitorStartDate.getValue().format('Y-m-d');
//    excep_endDate = monitorEndDate.getValue().format('Y-m-d');
    deviceMonitorParam = 'deviceMonitor';
//    openTab("终端异常告警查询","./baseApp/dataGatherMan/deviceException.jsp");
    dm_Window.close();
    openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
			"deviceAbnormal");
}
function genData(){
	Ext.Ajax.request({
		url:'baseapp/deviceMonitor!queryMonitorInfo.action',
		params:{monitorStartDate:monitorStartDate.getValue().format('Y-m-d'),monitorEndDate:monitorEndDate.getValue().format('Y-m-d')},
		success:function(response) {
			var result = Ext.decode(response.responseText);
			if(result!=null){
				levelArray = result.serisJsonList;
				factoryArray = result.factoryJsonList;
				genFusionChart();
				gridStore1.loadData(result);
				
				if(result.deviceFactoryList!=null){
					var dfArray = result.deviceFactoryList;
					var arr = [];
					for(var i=0;i<dfArray.length;i=i+1){
						var o = new Array();
						o.push(dfArray[i].orgName);
						for(var j=0;j<dfArray[i].factories.length;j++){
							o.push(dfArray[i].factories[j]);
						}
						arr.push(o);
					}
					var obj1 = new Object();
					obj1.deviceFactoryList = arr;
					gridStore2.loadData(obj1);
				}
			}
		}
	});
}

Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.Ajax.request({
				url:'baseapp/deviceMonitor!queryDeviceFactory.action',
				success:function(response) {
					var result = Ext.decode(response.responseText);
					if(result!=null && result.vwFactoryList!=null){
						var arrays = result.vwFactoryList;
						for(var i=0;i<arrays.length;i=i+1){
							arrayStore.push('factory'+(i+1));
							arrayCm.push({
								header:arrays[i].factoryName,
								dataIndex : 'factory'+(i+1),
								align :'center',
								width: 90,
								sortable : true
							});
						}
					}
				},
				callback:function(){
					var cm2 = new Ext.grid.ColumnModel(arrayCm);
					var objStore = new Object();
					objStore.proxy = new Ext.data.MemoryProxy();
					objStore.fields = arrayStore;
					objStore.root = 'deviceFactoryList';
					gridStore2 = new Ext.data.ArrayStore(objStore);
					var grid2 = new Ext.grid.GridPanel({
						store : gridStore2,
						title:'生产厂商',
						border : false,
						autoWidth : true,
						cm : cm2,
						stripeRows : true,
						autoScroll : true,
						viewConfig : {
							forceFit:false
						}
					});
					var monitorGrid = new Ext.TabPanel({
						border : false,
						region:'center',
						activeTab : 0,
						items : [grid1, grid2]
					});
					var monitorViewPanel = new Ext.form.FormPanel({
						border : false,
						frame:false,
						layout:'border',
						items : [monitorTop, monitorGrid]
					});
					renderModel(monitorViewPanel,'设备监测');
					genData();
				}
			});
		});