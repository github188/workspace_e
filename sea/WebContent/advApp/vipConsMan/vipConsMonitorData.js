//定义cm
var vcMonitorData_gridCm =new Ext.grid.ColumnModel([
   {
		header : "供电单位",
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'orgNo'
	}, {
		header : "用户编号",
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'consNo'
	}, {
		header : "用户名称",
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'consName'
	}
	, {
		header : "负荷曲线",//负荷曲线
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'loadCurve',
		renderer:function(s, m, rec){
			var href = '';
			href = href + "<a href='javascript:' onclick='"
							+ "openLoadCurve(\"" + rec.get('consNo') + "\",\""+rec.get('consName') + "\",\"" + rec.get('orgName') +"\");"
							+ "'><font color='green';font-weight:bold>[" + "查看"
							+ "]</font></a>" + "&nbsp;&nbsp;";
			return href;
	    }
	}, {
		header : "电能量曲线",//电能量曲线
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'energyCurve',
		renderer:function(s, m, rec){
			var href = '';
			href = href + "<a href='javascript:' onclick='"
							+ "openEnergyCurve(\"" + rec.get('consNo') + "\",\""+rec.get('consName') + "\",\"" + rec.get('orgName') +"\");"
							+ "'><font color='green';font-weight:bold>[" + "查看"
							+ "]</font></a>" + "&nbsp;&nbsp;";
			return href;
	    }
	}, {
		header : "实时工况",//实时工况
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'runStatus',
		renderer:function(s, m, rec){
			var href = '';
			href = href + "<a href='javascript:' onclick='"
							+ "openRunStatus(\"" + rec.get('consNo') + "\",\""+rec.get('consName')+"\");"
							+ "'><font color='green';font-weight:bold>[" + "查看"
							+ "]</font></a>" + "&nbsp;&nbsp;";
			return href;
	    }
	}, {
		header : "异常事件",//异常事件
		sortable : true,
		resizable : true,
		align : 'center',
		dataIndex : 'abnormalEvent',
		renderer:function(s, m, rec){
			var href = '';
			href = href + "<a href='javascript:' onclick='"
							+ "openAbnormalEvent(\"" + rec.get('consNo') + "\",\""+rec.get('consName')+"\");"
							+ "'><font color='green';font-weight:bold>[" + "查看"
							+ "]</font></a>" + "&nbsp;&nbsp;";
			return href;
	    }
	}]);

//定义Grid的store
var vcMonitorData_gridStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : './advapp/vipconsman/vipConsMonitorData!queryGridData.action'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'dataList',
			totalProperty : 'totalCount'
		}, [{
			name : 'orgNo'
		}, {
			name : 'orgName'
		}, {
			name : 'consNo'
		}, {
			name : 'consName'
		}, {
			name : 'loadCurve'
		}, {
			name : 'energyCurve'
		}, {
			name : 'runStatus'
		}, {
			name : 'abnormalEvent'
		}])
});

//grid面板
var vcMonitorData_gridPanel = new Ext.grid.GridPanel( {
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : false
		},
		cm : vcMonitorData_gridCm,
		store : vcMonitorData_gridStore,
		bbar : new Ext.ux.MyToolbar( {
			enableExpAll : true,
			store : vcMonitorData_gridStore
		}),
		tbar : [ {
			xtype : 'label',
			html : "<font font-weight:bold;>重点用户监测列表</font>"
		} ]
});

vcMonitorData_gridStore.removeAll();
vcMonitorData_gridStore.baseParams = {};
vcMonitorData_gridStore.load();

//负荷曲线链接
function openLoadCurve(consNo,consName,orgName){
	
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','户号为空，请重新选择用户！');
	    return;
    }
	
	var dataXml = getLoadCurveData('','' , '' ,'' , '',4,'', '', '');
	var width=0;
	var height=0;
	var colors = new Array('1D8BD1','F5A901','0025A5','FF8B71','F1683C','FAEEB5','ABEEBF','DEE8EF');
	var vipConsLoadArrayCm = new Array();
	var loadCurveChart;
	
	//表计类别
	var vipConsLoadStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : './advapp/vipconsman/vipConsMonitorData!queryAssetData.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'assetList'
			}, [{name:'assetNo'},{name:'dataSrc'}])
	});
	
	var vipConsLoadComboBox = new Ext.form.ComboBox({
		store : vipConsLoadStore,
		name :'vipConsLoadComboBox',
		fieldLabel:'表计资产编号',
		valueField : 'assetNo',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		emptyText:'请选择',
		displayField : 'assetNo',
		labelSeparator:'',
		width : 120
	});
	
	//查询日期
	var vipConsLoadQueryDate = new Ext.form.DateField({
		fieldLabel : '日期',
		name : 'vipConsLoadQueryDate',
		editable:false,
		width : 100,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date()
    });
	
	//上一天按钮
	var lastBtn = new Ext.Button({
		    text:' 上一天',
		    iconCls:'previous',
			name : 'lastBtn',
			width : 60,
		    labelSeparator:'',
		    handler:function(){
				var dataDate = vipConsLoadQueryDate.getValue().add(Date.DAY, -1);
				vipConsLoadQueryDate.setValue(dataDate);
				queryData();
	        }
	});
	
	//下一天按钮
	var nextBtn = new Ext.Button({
		    text:' 下一天',
		    iconCls:'next',
			name : 'nextBtn',
			width : 60,
		    labelSeparator:'',
		    handler:function(){
				var dataDate = vipConsLoadQueryDate.getValue().add(Date.DAY, 1);
				vipConsLoadQueryDate.setValue(dataDate);
				queryData();
	        }
	});
	
	// 定义checkBox,用于是否显示冻结数据
	var statCheckbox = new Ext.form.Checkbox( {
		boxLabel : '冻结',
		name : 'statCurve',
		inputValue : 1
	});
	
	// 定义checkBox,用于是否显示冻结数据
	var realCheckbox = new Ext.form.Checkbox( {
		boxLabel : '实时',
		checked : true,
		name : 'realCurve',
		inputValue : 1
	});
	
	//查询按钮
	var queryBtn = new Ext.Button({
			text : '查询',
			name : 'queryBtn',
			width : 60,
		    labelSeparator:'',
		    handler:function(){
				queryData();
	        }
	});
	
	//退出按钮
	var exitBtn = new Ext.Button({
			text : '退出',
			name : 'exitBtn',
			width : 60,
		    labelSeparator:'',
		    handler:function(){
		        vipConsLoadWindow.close();
	        }
	});
	
	//查询条件面板
	var vipConsLoadCondPnl=new Ext.Panel({
		border:false,
		height:40,
		layout:'table',
		layoutConfig : {
			columns :8
	    },
	    defaults: {height: 35},
		items:[{
	            border : false,
	            layout:'form',
	            width:70,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 5px',
		        items:[lastBtn]
		    },{
	            border : false,
	            layout:'form',
	            width:150,
	            labelWidth : 35,
	  	        bodyStyle : 'padding:10px 0px 0px 5px',
		        items:[vipConsLoadQueryDate]
			},{
	            border : false,
	            layout:'form',
	            width:65,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[nextBtn]
		    },{
	            border : false,
	            layout:'form',
	            width:220,
	            labelWidth : 80,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[vipConsLoadComboBox]
			},{
	            border : false,
	            layout:'form',
	            width:50,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[statCheckbox]
			},{
	            border : false,
	            layout:'form',
	            width:50,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[realCheckbox]
			},{
	            border : false,
	            layout:'form',
	            width:100,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[queryBtn]
			},{
	            border : false,
	            layout:'form',
	            width:100,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[exitBtn]
			}]
	});
	
	// 定义三维图标panel
	var curvePaintPanel = new Ext.Panel( {
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : false
	});
	
	//曲线图
	var curvePanel = new Ext.Panel( {
		title : "曲线图",
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : true,
		style : "padding:2px",
		items : [ curvePaintPanel ]
	});
	
	// 用于实现fusionchart动态变化
	curvePaintPanel.on("afterlayout", function(view, layout) {
		width = curvePanel.getWidth()-6;
		height = curvePanel.getHeight() - 10;
		loadCurveChart = new FusionCharts("fusionCharts/MSLine.swf",
				"loadCurveChartId", width, height);
		loadCurveChart.setDataXML(dataXml);
		loadCurveChart.setTransparent(true);
		loadCurveChart.render(curvePaintPanel.getId());// 渲染
		}, curvePaintPanel);
	
	//曲线Grid数据store
	var vipConsLoadGridStore = new Ext.data.Store({
	 	        proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'powerCurveList'
						}, [
						   {name : 'time'},
						   {name : 'statP'},
						   {name : 'statPa'},
						   {name : 'statPb'}, 
						   {name : 'statPc'}, 
						   {name : 'statQ'}, 
						   {name : 'statQa'}, 
						   {name : 'statQb'},
						   {name : 'statQc'},
						   {name : 'realP'},
						   {name : 'realPa'},
						   {name : 'realPb'}, 
						   {name : 'realPc'}, 
						   {name : 'realQ'}, 
						   {name : 'realQa'}, 
						   {name : 'realQb'},
						   {name : 'realQc'}
						   ])
	});
	
	//曲线Grid数据cm
	var vipConsLoadCm = new Ext.grid.ColumnModel([{
			header : '时间点',
			dataIndex : 'time',
			sortable : true,
			width: 120,
			align : 'center'
		}]);

	var vipConsLoadGridPanel = new Ext.grid.GridPanel({
        autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : false
		},
		cm : vipConsLoadCm,
		store : vipConsLoadGridStore
	});

	var vipConsLoadTabPnl = new Ext.TabPanel({
		activeTab : 0,
		height:480,
		border : false,
		defaults : {
			autoScroll : true
		},
		items : [curvePanel
			 ,  {
					title : '曲线数据',
					border : false,
					baseCls : "x-plain",
					layout : 'fit',
					autoScroll : true,
					items : [vipConsLoadGridPanel]
				}]
	});
	
	var vipConsLoadWindow = new Ext.Window({
		frame:true,
		width:800,
		height:550,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"close",//将窗体隐藏而并不销毁
		title:'',
		items:[vipConsLoadCondPnl,vipConsLoadTabPnl]
	});
	
	var winTitle = "【供电单位】";
	if(!Ext.isEmpty(orgName) && 'null' != orgName){
		winTitle += orgName;
	}
	if('null' != consNo){
		winTitle += "【户号】" + consNo;
	}
	if(!Ext.isEmpty(consName) && 'null' != consName){
		winTitle += "【户名】" + consName;
	}
	
	vipConsLoadWindow.setTitle(winTitle);
	vipConsLoadStore.removeAll();
	vipConsLoadComboBox.clearValue();
	vipConsLoadStore.baseParams = {
			consNo : consNo
    };			
	vipConsLoadStore.load();
	vipConsLoadWindow.show();
	
	//动态生成负荷cm
	function generateDynamicPowerCm(statFlag,realFlag){
		if(!Ext.isEmpty(vipConsLoadCm)){
			vipConsLoadArrayCm.length = 0;
		}
		vipConsLoadArrayCm.push({
			header : '时间点',
			dataIndex : 'time',
			sortable : true,
			width: 120,
			align : 'center'
		});
		if(statFlag){
			vipConsLoadArrayCm.push({
				header : '冻结有功功率',
				dataIndex : 'statP',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结A相有功功率',
				dataIndex : 'statPa',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结B相有功功率',
				dataIndex : 'statPb',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结C相有功功率',
				dataIndex : 'statPc',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结无功功率',
				dataIndex : 'statQ',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结A相无功功率',
				dataIndex : 'statQa',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结B相无功功率',
				dataIndex : 'statQb',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '冻结C相无功功率',
				dataIndex : 'statQc',
				sortable : true,
				width: 120,
				align : 'center'
			});
		}
		if(realFlag){
			vipConsLoadArrayCm.push({
				header : '实时有功功率',
				dataIndex : 'realP',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时A相有功功率',
				dataIndex : 'realPa',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时B相有功功率',
				dataIndex : 'realPb',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时C相有功功率',
				dataIndex : 'realPc',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时无功功率',
				dataIndex : 'realQ',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时A相无功功率',
				dataIndex : 'realQa',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时B相无功功率',
				dataIndex : 'realQb',
				sortable : true,
				width: 120,
				align : 'center'
			});
			vipConsLoadArrayCm.push({
				header : '实时C相无功功率',
				dataIndex : 'realQc',
				sortable : true,
				width: 120,
				align : 'center'
			});
		}
		vipConsLoadCm = new Ext.grid.ColumnModel(vipConsLoadArrayCm);
	}
	
	function queryData(){
		if(!statCheckbox.getValue() && !realCheckbox.getValue()){
			Ext.MessageBox.alert('提示', '请勾选实时或冻结数据！');
			return;
		}
		var assetNo = vipConsLoadComboBox.getValue();
        if(Ext.isEmpty(assetNo)
        		|| "" == assetNo){
        	Ext.MessageBox.alert('提示', '请选择表计！');
			return;
        }
        generateDynamicPowerCm(statCheckbox.getValue(), realCheckbox.getValue());
		Ext.Ajax.request({
			url : "./advapp/vipconsman/vipConsMonitorData!queryLoadData.action",
			params : {
				consNo : consNo,
				assetNo: vipConsLoadComboBox.getValue(),
				date:vipConsLoadQueryDate.getValue(),
				realFlag:realCheckbox.getValue(),
				statFlag:statCheckbox.getValue()
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var timeModelList = result.timeModelList;
				var yAxisMaxValue = result.maxValue;
				var yAxisMinValue = result.minValue;
				if (null == timeModelList) {//如果时间模为空，则返回
					return;
				}
				var statPowerBean = result.statPowerBean;
		        var realPowerBean = result.realPowerBean;
		        var realPowerName = result.realPowerName;
		        
				dataXml = getLoadCurveData(timeModelList,statPowerBean , realPowerBean ,
						realPowerName , colors ,4, "", yAxisMaxValue, yAxisMinValue);
				loadCurveChart = new FusionCharts(
									"fusionCharts/MSLine.swf",
									"loadCurveChartId", width, height);
				loadCurveChart.setDataXML(dataXml);
				loadCurveChart.setTransparent(true);
				loadCurveChart.render(curvePaintPanel.getId());// 渲染
				
				vipConsLoadGridStore.removeAll();
				vipConsLoadGridStore.loadData(result);
				vipConsLoadGridPanel.reconfigure(vipConsLoadGridStore,vipConsLoadCm);
			}
        });
	}
}

//电能量曲线链接
function openEnergyCurve(consNo,consName,orgName){
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','户号为空，请重新选择用户！');
	    return;
    }
	
	var width=0;
	var height=0;
	var energyCurveChart;
	var colors = new Array('1D8BD1','F5A901','0025A5','FF8B71');
	var dataXml = getEnergyCurveData('','' ,'', 4, '', '', '');
	
	//表计类别
	var vipConsEnergyStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : './advapp/vipconsman/vipConsMonitorData!queryAssetData.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'assetList'
			}, [{name:'assetNo'},{name:'dataSrc'}])
	});
	
	var vipConsEnergyComboBox = new Ext.form.ComboBox({
		store : vipConsEnergyStore,
		name :'vipConsEnergyComboBox',
		fieldLabel:'表计资产编号',
		valueField : 'assetNo',
		editable : false,
		triggerAction : 'all',
		forceSelection : true,
		mode : 'remote',
		selectOnFocus : true,
		emptyText:'请选择',
		displayField : 'assetNo',
		labelSeparator:'',
		width : 120
	});
	
	//查询日期
	var vipConsEnergyQueryDate = new Ext.form.DateField({
		fieldLabel : '日期',
		name : 'vipConsEnergyQueryDate',
		editable:false,
		width : 100,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date()
    });
	
	//查询按钮
	var queryBtn = new Ext.Button({
			text : '查询',
			name : 'queryBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
		        queryData();
	        }
	});
	
	//退出按钮
	var exitBtn = new Ext.Button({
			text : '退出',
			name : 'exitBtn',
			width : 80,
		    labelSeparator:'',
		    handler:function(){
		        vipConsEnergyWindow.close();
	        }
	});
	
	//上一天按钮
	var lastBtn = new Ext.Button({
		    text:' 上一天',
		    iconCls:'previous',
			name : 'lastBtn',
			width : 65,
		    labelSeparator:'',
		    handler:function(){
				var dataDate = vipConsEnergyQueryDate.getValue().add(Date.DAY, -1);
				vipConsEnergyQueryDate.setValue(dataDate);
				queryData();
	        }
	});
	
	//下一天按钮
	var nextBtn = new Ext.Button({
		    text:' 下一天',
		    iconCls:'next',
			name : 'nextBtn',
			width : 65,
		    labelSeparator:'',
		    handler:function(){
				var dataDate = vipConsEnergyQueryDate.getValue().add(Date.DAY, 1);
				vipConsEnergyQueryDate.setValue(dataDate);
				queryData();
	        }
	});
	
	//查询条件面板
	var vipConsEnergyCondPnl=new Ext.Panel({
		border:false,
		height:40,
		layout:'table',
		layoutConfig : {
			columns :6
	    },
	    defaults: {height: 35},
		items:[{
	            border : false,
	            layout:'form',
	            width:70,
	            hideLabels:true,
	  	        bodyStyle : 'padding:10px 0px 0px 5px',
		        items:[lastBtn]
		    },{
	            border : false,
	            layout:'form',
	            width:150,
	            labelWidth : 35,
	  	        bodyStyle : 'padding:10px 0px 0px 5px',
		        items:[vipConsEnergyQueryDate]
			}, {
	            border : false,
	            layout:'form',
	            width:65,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[nextBtn]
		    },{
	            border : false,
	            layout:'form',
	            width:220,
	            labelWidth : 80,
	  	        bodyStyle : 'padding:10px 0px 0px 5px',
		        items:[vipConsEnergyComboBox]
			},{
	            border : false,
	            layout:'form',
	            width:100,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[queryBtn]
			},{
	            border : false,
	            layout:'form',
	            width:100,
	  	        bodyStyle : 'padding:10px 0px 0px 0px',
		        items:[exitBtn]
			}]
	});
	
	// 定义三维图标panel
	var curvePaintPanel = new Ext.Panel( {
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : false
	});
	
	//曲线图
	var curvePanel = new Ext.Panel( {
		title : "曲线图",
		border : false,
		bodyBorder : false,
		layout : 'fit',
		monitorResize : true,
		style : "padding:2px",
		items : [ curvePaintPanel ]
	});
	
	// 用于实现fusionchart动态变化
	curvePaintPanel.on("afterlayout", function(view, layout) {
		width = curvePanel.getWidth()-6;
		height = curvePanel.getHeight() - 10;
		energyCurveChart = new FusionCharts("fusionCharts/MSLine.swf",
				"loadCurveChartId", width, height);
		energyCurveChart.setDataXML(dataXml);
		energyCurveChart.setTransparent(true);
		energyCurveChart.render(curvePaintPanel.getId());// 渲染
		}, curvePaintPanel);
	
	//曲线Grid数据store
	var vipConsEnergyGridStore = new Ext.data.Store({
	 	        proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'energyCurveList'
						}, [
						   {name : 'time'},
						   {name : 'papE'},
						   {name : 'prpE'},
						   {name : 'rapE'}, 
						   {name : 'rrpE'}
						   ])
	});
	
	//曲线Grid数据cm
	var vipConsEnergyCm = new Ext.grid.ColumnModel([{
			header : '时间点',
			dataIndex : 'time',
			sortable : true,
			width: 120,
			align : 'center'
		},{
			header : '正向有功',
			dataIndex : 'papE',
			sortable : true,
			width: 120,
			align : 'center'
		},{
			header : '正向无功',
			dataIndex : 'prpE',
			sortable : true,
			width: 120,
			align : 'center'
		},{
			header : '反向有功',
			dataIndex : 'rapE',
			sortable : true,
			width: 120,
			align : 'center'
		},{
			header : '反向无功',
			dataIndex : 'rrpE',
			sortable : true,
			width: 120,
			align : 'center'
		}]);

	var vipConsEnergyGridPanel = new Ext.grid.GridPanel({
        autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : false
		},
		cm : vipConsEnergyCm,
		store : vipConsEnergyGridStore
	});

	var vipConsEnergyTabPnl = new Ext.TabPanel({
		activeTab : 0,
		height:480,
		border : false,
		defaults : {
			autoScroll : true
		},
		items : [curvePanel
			 ,  {
					title : '曲线数据',
					border : false,
					baseCls : "x-plain",
					layout : 'fit',
					autoScroll : true,
					items : [vipConsEnergyGridPanel]
				}]
	});
	
	var vipConsEnergyWindow = new Ext.Window({
		frame:true,
		width:800,
		height:550,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"close",//将窗体隐藏而并不销毁
		title:'',
		items:[vipConsEnergyCondPnl,vipConsEnergyTabPnl]
	});
	
	var winTitle = "【供电单位】";
	if(!Ext.isEmpty(orgName) && 'null' != orgName){
		winTitle += orgName;
	}
	if('null' != consNo){
		winTitle += "【户号】" + consNo;
	}
	if(!Ext.isEmpty(consName) && 'null' != consName){
		winTitle += "【户名】" + consName;
	}
	
	vipConsEnergyWindow.setTitle(winTitle);
	vipConsEnergyStore.removeAll();
	vipConsEnergyComboBox.clearValue();
	vipConsEnergyStore.baseParams = {
			consNo : consNo
    };			
	vipConsEnergyStore.load();
	vipConsEnergyWindow.show();
	
	function queryData(){
		var assetNo = vipConsEnergyComboBox.getValue();
        if(Ext.isEmpty(assetNo)
        		|| "" == assetNo){
        	Ext.MessageBox.alert('提示', '请选择表计！');
			return;
        }
		Ext.Ajax.request({
			url : "./advapp/vipconsman/vipConsMonitorData!queryEnergyData.action",
			params : {
				consNo : consNo,
				assetNo: vipConsEnergyComboBox.getValue(),
				date:vipConsEnergyQueryDate.getValue()
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var timeModelList = result.timeModelList;
				var yAxisMaxValue = result.maxValue;
				var yAxisMinValue = result.minValue;
				if (null == timeModelList) {//如果时间模为空，则返回
					return;
				}
				var energyBean = result.energyBean;
				dataXml = getEnergyCurveData(timeModelList,energyBean , colors,4 , '',yAxisMaxValue, yAxisMinValue);
				energyCurveChart = new FusionCharts(
									"fusionCharts/MSLine.swf",
									"energyCurveChartId", width, height);
				energyCurveChart.setDataXML(dataXml);
				energyCurveChart.setTransparent(true);
				energyCurveChart.render(curvePaintPanel.getId());// 渲染		
				
				vipConsEnergyGridStore.loadData(result);
			}
        });
	}
}

//实时工况链接
function openRunStatus(consNo,consName){
	vipConsMonitorData_consNo = consNo;
	vipConsMonitorData_consName = consName;
	vipConsMonitorData_Flag = true;
	openTab("终端设备运行状态", "./runMan/runStatusMan/terminalRunStatus.jsp",false,"terminalRunStatus");
}

//异常事件链接
function openAbnormalEvent(consNo,consName){
	vipConsMonitorData_consNo1 = consNo;
	vipConsMonitorData_consName1 = consName;
	vipConsMonitorData_Flag1 = true;
	openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
			"deviceAbnormal");
}

//生成负荷数据曲线数据
function getLoadCurveData(timeModelList,statPowerBean , realPowerBean ,realPowerName , colors,step,publicName, yAxisMaxValue, yAxisMinValue){
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

	var str = "<categories>";
	Ext.each(timeModelList,function(obj){
	    str += "<category label='"+obj['time']+"' />";
	});
	str += "</categories>";
	
	xmlData = xmlData+str;
	var i = 0 ;		
	if(!Ext.isEmpty(statPowerBean )){
   	 Ext.each(statPowerBean,function(obj){
   		color = colors[i];
   		if(!Ext.isEmpty(obj['curveName'])){
   			 var str1 = "<dataset seriesName='" + obj['curveName'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
   		     Ext.each(obj['curveList'],function(obj1){
   		          if(obj1['flag']){
   		               str1 += "<set />";
   		          }else{
   		        	   str1 += "<set value='"+obj1['data']+"'/>";
   		          }
   		     });
   		     str1 += "</dataset>";
   		     xmlData = xmlData+str1;
   		     i++;
   		 }
        });
    }
	
	if(null != realPowerBean && 0 < realPowerBean.length){
        var str1 = "<dataset seriesName='实时有功功率'"+" color='58539B' anchorBorderColor='58539B' anchorBgColor='58539B'>";
        Ext.each(realPowerBean,function(obj){
        	if(obj['flagP']){
                str1 += "<set />";
        	}else{
        		str1 += "<set value='"+obj['p']+"'/>";
        	}
         });
        str1 += "</dataset>";
        	
	    var str2 = "<dataset seriesName='实时A相有功功率' color='00BA00' anchorBorderColor='00BA00' anchorBgColor='00BA00'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagPA']){
                str2 += "<set />";
        	}else{
        		str2 += "<set value='"+obj['pA']+"'/>";
        	}
	    });
	    str2 += "</dataset>";
	    
	    var str3 = "<dataset seriesName='实时B相有功功率' color='FF0000' anchorBorderColor='FF0000' anchorBgColor='FF0000'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagPB']){
                str3 += "<set />";
        	}else{
        		str3 += "<set value='"+obj['pB']+"'/>";
        	}
	    });
	    str3 += "</dataset>";
	    
	    var str4 = "<dataset seriesName='实时C相有功功率' color='800080' anchorBorderColor='800080' anchorBgColor='800080'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagPC']){
                str4 += "<set />";
        	}else{
        		str4 += "<set value='"+obj['pC']+"'/>";
        	}
	    });
	    str4 += "</dataset>";
	    
	    var str5 = "<dataset seriesName='实时无功功率'"+" color='62428F' anchorBorderColor='62428F' anchorBgColor='62428F'>";
        Ext.each(realPowerBean,function(obj){
        	if(obj['flagQ']){
                str5 += "<set />";
        	}else{
        		str5 += "<set value='"+obj['q']+"'/>";
        	}
         });
        str5 += "</dataset>";
        	
	    var str6 = "<dataset seriesName='实时A相无功功率' color='EA8908' anchorBorderColor='EA8908' anchorBgColor='EA8908'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagQA']){
                str6 += "<set />";
        	}else{
        		str6 += "<set value='"+obj['qA']+"'/>";
        	}
	    });
	    str6 += "</dataset>";
	    
	    var str7 = "<dataset seriesName='实时B相无功功率' color='5C589F' anchorBorderColor='5C589F' anchorBgColor='5C589F'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagQB']){
                str7 += "<set />";
        	}else{
        		str7 += "<set value='"+obj['qB']+"'/>";
        	}
	    });
	    str7 += "</dataset>";
	    
	    var str8 = "<dataset seriesName='实时C相无功功率' color='4974CC' anchorBorderColor='4974CC' anchorBgColor='4974CC'>";
	    Ext.each(realPowerBean,function(obj){
	    	if(obj['flagQC']){
                str8 += "<set />";
        	}else{
        		str8 += "<set value='"+obj['qC']+"'/>";
        	}
	    });
	    str8 += "</dataset>";
	    
	    xmlData = xmlData+str1+str2+str3+str4+str5+str6+str7+str8;
    }
	
	xmlData = xmlData+"<styles><definition>"+
	    "<style name='CaptionFont' type='font' size='12' />"+
	    "</definition><application>"+
	    "<apply toObject='CAPTION' styles='CaptionFont' />"+ 
	    "<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
	    "</application></styles></chart>";

	return xmlData;	
}

function getEnergyCurveData(timeModelList,energyBean , colors, step,publicName, yAxisMaxValue, yAxisMinValue){
	var xmlData = "<chart caption='" + publicName + "' legendPosition='BOTTUM' lineThickness='1' showValues='0' formatNumberScale='0'" +
	    " anchorRadius='1' divLineAlpha='10' divLineColor='CC3300' " +
	    "divLineIsDashed='1' showAlternateHGridColor='1' " +
	    "alternateHGridAlpha='5' alternateHGridColor='CC3300'" +
	    " shadowAlpha='40' labelStep='" + step + "' numvdivlines='22' " +
	    "chartRightMargin='35' bgColor='FFFFFF,CC3300' " +
	    "bgAngle='270' bgAlpha='10,10' yAxisMinValue='" + yAxisMinValue + "' yAxisMaxValue='" + yAxisMaxValue + "' >";

	var str = "<categories>";
	Ext.each(timeModelList,function(obj){
	    str += "<category label='"+obj['time']+"' />";
	});
	str += "</categories>";
	
	xmlData = xmlData+str;
	var i = 0 ;		
	if(!Ext.isEmpty(energyBean )){
		 Ext.each(energyBean,function(obj){
			color = colors[i];
			if(!Ext.isEmpty(obj['curveName'])){
				 var str1 = "<dataset seriesName='" + obj['curveName'] +"'"+" color='"+color+"' anchorBorderColor='"+color+"' anchorBgColor='"+color+"'>";
			     Ext.each(obj['curveList'],function(obj1){
			          if(obj1['flag']){
			               str1 += "<set />";
			          }else{
			        	   str1 += "<set value='"+obj1['data']+"'/>";
			          }
			     });
			     str1 += "</dataset>";
			     xmlData = xmlData+str1;
			     i++;
			 }
	    });
	}
	xmlData = xmlData+"<styles><definition>"+
		"<style name='CaptionFont' type='font' size='12' />"+
		"</definition><application>"+
		"<apply toObject='CAPTION' styles='CaptionFont' />"+ 
		"<apply toObject='SUBCAPTION' styles='CaptionFont' />"+
		"</application></styles></chart>";
	
	return xmlData;	
}