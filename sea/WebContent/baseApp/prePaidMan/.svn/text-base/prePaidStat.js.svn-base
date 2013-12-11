/*
 * ! Author: Longkongcao Date:11/27/2009 Description: 预付费执行统计 Update History:
 * none
 */
Ext.onReady(function() {
	// var dwDataStore = new Ext.data.ArrayStore({
	// fields : ['flgID', 'flgText'],
	// data : [['flgData1', '浦口'], ["flgData2", '雨花台']]
	// })
	//
	// // 供电单位combobox
	// var dwComboBox = new Ext.form.ComboBox({
	// fieldLabel : '供电单位',
	// labelSeparator : '',
	// store : dwDataStore,
	// bodyStyle : 'padding:10px;',
	// triggerAction : 'all',
	// mode : 'local',
	// valueField : 'flgID',
	// displayField : 'flgText',
	// anchor : '90%',
	// emptyText : 'Select a state...',
	// selectOnFocus : true
	// });
	
	//全局变量
    var nodeId;// 左边树选择编号
    var NodeType;  // 左边树节点类型
	var height = 0;
	var width = 0;
	
	//fushionChart数据
	var csKeys = new Array('busiType','prePaidNum');
	var csArray = [];
	var esKeys = new Array('execType','feeCnt');
	var esArray = [];	
	
	//节点名
    var orgNameField =new Ext.form.TextField({
		labelSeparator : '',
		fieldLabel : '节点名',
		emptyText : '请从左边树选择地市或区县的供电单位',
	    allowBlank : false,
	    blankText : '请从左边树选择地市或区县的供电单位',
		readOnly : true,
		width:230
									
    });
    //开始日期
    var startDateField =new Ext.form.DateField({
		labelSeparator : '',
		fieldLabel : '从',
		name : 'ppsStartDate',
		value : new Date()
				.add(Date.DAY, -7),
		format : 'Y-m-d',
		editable:false,
		width:100
    });
    //结束日期
    var endDateField =new Ext.form.DateField({
		labelSeparator : '',
		fieldLabel : '至',
		value : new Date(),
		format : 'Y-m-d',
		editable:false,
		width:100							
    });
    
    //查询按钮
    var queryButton=new Ext.Button({
		xtype : 'button',
		width : '70',
		text : '查询',
		handler:function(){
			if(''==orgNameField.getValue().trim()){
				Ext.MessageBox.alert("提示","请从左边树选择地市或区县的供电单位！");
			    return;
			}
			var startDate=startDateField.getValue().format('Y-m-d');
			var endDate=endDateField.getValue().format('Y-m-d');
			if(startDate>endDate){
				Ext.MessageBox.alert("提示","开始日期不能大于结束日期！");
			    return;
			}
		    prePaidstatStore.removeAll();
	        execStatStore.removeAll();
    	    csArray.length = 0;
    	    esArray.length = 0;
			Ext.getBody().mask("正在获取数据...");
			Ext.Ajax.request({
				url:'./baseapp/prePaidStat!prePaidStat.action',
	     		params : {
	                orgNo:nodeId, 
	                orgType:nodeType,
	     			startDate:startDate,
	     			endDate:endDate
	     		},
				success : function(response){
					var result = Ext.decode(response.responseText);
					if(result){
						if(null!=result.prePaidCtrlStatBeanList && 0 < result.prePaidCtrlStatBeanList.length){
							prePaidstatStore.loadData(result.prePaidCtrlStatBeanList);
							var obj = new Object();
							obj.busiType =  '主站预付费';
							obj.prePaidNum = result.prePaidCtrlStatBeanList[0].stationPrePaidNum;
							csArray.push(obj);
							obj = new Object();
							obj.busiType =  '终端预付费';
							obj.prePaidNum = result.prePaidCtrlStatBeanList[0].tmnlPrePaidNum;
							csArray.push(obj);
							obj = new Object();
							obj.busiType =  '电表预付费';
							obj.prePaidNum =  result.prePaidCtrlStatBeanList[0].meterPrePaidNum;
							csArray.push(obj);
						}
						if(null!=result.prePaidExecStatBeanList && 0 < result.prePaidExecStatBeanList.length){
							execStatStore.loadData(result.prePaidExecStatBeanList);
							var obj = new Object();
							obj.execType =  '成功';
							obj.feeCnt = result.prePaidExecStatBeanList[0].succFeeCnt;
							esArray.push(obj);
							obj = new Object();
							obj.execType =  '失败';
							obj.feeCnt = result.prePaidExecStatBeanList[0].failFeeCnt;
							esArray.push(obj);
							obj = new Object();
							obj.execType =  '未完成';
							obj.feeCnt =  result.prePaidExecStatBeanList[0].uncompleteFeeCnt;
							esArray.push(obj);
						}
					}
					generateCsChart(width, height);	
					generateEsChart(width, height);
				},
				callback:function(){
					Ext.getBody().unmask();
				}
			});
		}						
    });
    
    //查询条件
	var formPanel = new Ext.Panel({
				labelAlign : 'right',
				border : false,
				region : 'north',
				layout : 'table',
				layoutConfig : {
					columns : 4
				},
				defaults: {height: 35},
				height : 35,
				// id : 'ppsPanel',
				bodyStyle : 'padding:10px 10px 10px 0px ',
				items : [{
							layout : 'form',
							border : false,
							labelWidth : 60,
							labelAlign : 'right',
							width : 380,
							items : [orgNameField]
						},{
							layout : 'form',
							border : false,
							labelWidth : 10,
							labelAlign : 'right',
							width : 115,
							items : [startDateField]
						},{
							layout : 'form',
							border : false,
							labelWidth : 15,
							labelAlign : 'right',
							width : 195,
							items : [endDateField]
						},{
							layout : 'form',
							border : false,
							width : 90,
							items : [queryButton]
						}]
			});
	
	var csChartPanel = new Ext.Panel({
			    labelAlign : 'left',
				border : false
			});

	var esChartPanel = new Ext.Panel({
				labelAlign : 'left',
				border : false
			});

	var chartPanel = new Ext.Panel({
				labelAlign : 'left',
				border : false,
				region : 'center',
				layout : 'column',
				bodyStyle : 'padding:5px;',
				items : [{
							layout : 'fit',
							border : false,
							columnWidth : .5,
							items : [csChartPanel]
						}, {
							layout : 'fit',
							border : false,
							columnWidth : .5,
							items : [esChartPanel]
						}]
			});
			
    //用于实现fusionchart动态变化
	csChartPanel.on("afterlayout", function(view,layout){
		width = chartPanel.getWidth() / 2;
		height = chartPanel.getHeight();
	    generateCsChart(width, height);
	},csChartPanel);
	
	esChartPanel.on("afterlayout", function(view,layout){
		width = chartPanel.getWidth() / 2;
		height = chartPanel.getHeight();
	    generateEsChart(width, height);
	},esChartPanel);
	
	var csChart;
	var esChart;
	function generateCsChart(width, height){
		var xmlData = getSingleXMLData(csArray,csKeys,'预付费控制类别统计','','');
		csChart = new FusionCharts("fusionCharts/Pie3D.swf", "csChart",
			width, height);
		csChart.setDataXML(xmlData);
		csChart.setTransparent(true);
		if(null != csChart){
			csChart.render(csChartPanel.getId());
		}
    }
    
    function generateEsChart(width, height){
    	var xmlData = getSingleXMLData(esArray,esKeys,'预付费执行统计','','');
    	esChart = new FusionCharts("fusionCharts/Pie3D.swf",
			"esChart", width, height);
		esChart.setDataXML(xmlData);
		esChart.setTransparent(true);
		if(null != esChart){
			esChart.render(esChartPanel.getId());
		}
    }
	
	var northPanel = new Ext.Panel({
				border : false,
				layout : 'border',
				region : 'center',
				items : [formPanel, chartPanel]
			});
	var ctrlTypeStatCm = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center'
			}, {
				header : '预付费汇总数',
				dataIndex : 'prePaidNum',
				align : 'center'
			}, {
				header : '主站预付费户数',
				dataIndex : 'stationPrePaidNum',
				align : 'center'
			}, {
				header : '终端预付费户数',
				dataIndex : 'tmnlPrePaidNum',
				align : 'center'
			}, {
				header : '电表预付费户数',
				dataIndex : 'meterPrePaidNum',
				align : 'center'
			}]);

	var execStatCm = new Ext.grid.ColumnModel([{
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center'
			}, {
				header : '执行成功购电笔数',
				dataIndex : 'succFeeCnt',
				align : 'center',
				renderer: function(s, m, rec){
				 		return "<a href='javascript:'onclick='prePaidExecStatQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ startDateField.getValue().format('Y-m-d')
							+ "\",\""
							+ endDateField.getValue().format('Y-m-d')
							+ "\",\"1\",\"执行成功购电记录\");'>" + s + "</a>"; 
			    } 
			}, {
				header : '执行失败购电笔数',
				dataIndex : 'failFeeCnt',
				align : 'center',
				renderer: function(s, m, rec){
				 		return "<a href='javascript:'onclick='prePaidExecStatQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ startDateField.getValue().format('Y-m-d')
							+ "\",\""
							+ endDateField.getValue().format('Y-m-d')
							+ "\",\"2\",\"执行失败购电记录\");'>" + s + "</a>"; 
			    } 
			}, {
				header : '未完成执行购电笔数',
				dataIndex : 'uncompleteFeeCnt',
				align : 'center',
				renderer: function(s, m, rec){
				 		return "<a href='javascript:'onclick='prePaidExecStatQueryShow(\""
							+ rec.get('orgNo')
							+ "\",\""
							+ rec.get('orgType')
							+ "\",\""
							+ startDateField.getValue().format('Y-m-d')
							+ "\",\""
							+ endDateField.getValue().format('Y-m-d')
							+ "\",\"3\",\"执行未完成购电记录\");'>" + s + "</a>"; 
			    } 
			}, {
				header : '执行成功率(%)',
				dataIndex : 'succRatio',
				align : 'center'
			}]);
		
    
    var prePaidstatStore = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.JsonReader({
				idProperty:'orgNo'
			}, [{
					name : 'orgNo'
				},{
					name : 'orgName'
				}, {
					name : 'prePaidNum'
				},{
					name : 'stationPrePaidNum'
				},{
					name : 'tmnlPrePaidNum'
				},{
					name : 'meterPrePaidNum'
				}])
    });
    
    var execStatStore = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(),
		reader : new Ext.data.JsonReader({
				idProperty:'orgNo'
			}, [{
					name : 'orgType'
				},{
					name : 'orgNo'
				},{
					name : 'orgName'
				},{
					name : 'succFeeCnt'
				}, {
					name : 'failFeeCnt'
				}, {
					name : 'uncompleteFeeCnt'
				}, {
					name : 'succRatio'
				}])
    });
	
	var ctrlTypeStatGrid = new Ext.grid.GridPanel({
				border : false,
				store : prePaidstatStore,
				cm : ctrlTypeStatCm,
				region : 'center',
				loadMask:false
			});
	
	var execStatGrid = new Ext.grid.GridPanel({
				border : false,
				store : execStatStore,
				cm : execStatCm,
				region : 'center',
				loadMask:false
			});
	
	// 下方的tab页
	var userTab = new Ext.TabPanel({
				activeTab : 0,
				region : 'south',
				height:200, 
				items : [{
							xtype : "panel",
							title : "预付费控制类别统计",
							layout : 'fit',
							border : false,
							items : [ctrlTypeStatGrid]
						}, {
							xtype : "panel",
							title : "预付费执行统计",
							layout : 'fit',
							border : false,
							items : [execStatGrid]
						}]
			});
			
	var ppsPanel = new Ext.Panel({
				//title : '查询条件',
				border : false,
				layout : 'border',
				items : [northPanel, userTab]
			});
	
	renderModel(ppsPanel, '预付费执行统计');

	
	// 监听左边树点击事件
	var pStatTreeListener = new LeftTreeListener( {
		modelName : '预付费执行统计',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			if (type == 'org'&&('03'==obj.orgType||'04'==obj.orgType)){
				nodeId = obj.orgNo;
				nodeType=obj.orgType;
				orgNameField.setValue(node.text);
			}
		}
	});
});

//预付费执行统计明细查询窗口显示
function prePaidExecStatQueryShow(orgNo,orgType,startDate,endDate,execStatus,title){
		var queryResultRowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if(queryResultStore && queryResultStore.lastOptions && queryResultStore.lastOptions.params){
					startRow = queryResultStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
		
		var queryResultCm = new Ext.grid.ColumnModel([  
	        queryResultRowNum,                                         
		    {header : '供电单位', sortable: true, dataIndex : 'orgName', align : 'center', 
		    	renderer : function(value) {
		    		if(null!=value){
						var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
			    	}
			    	else
			    		return '';
		    		}},
		    {header : '购电单号', sortable: true, dataIndex : 'appNo', align : 'center'},
		    {header : '用户编号', sortable: true, dataIndex : 'consNo', align : 'center'}, 
		    {header : '用户名称', sortable: true, dataIndex : 'consName', align : 'center',
		   		 renderer : function(value) {
		   		 	 if(null!=value){
						var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
			   		 }
			   		 else
			   		 	return '';
		   		 	 }},
		    {header : '终端地址', sortable: true, dataIndex : 'terminalAddr', align : 'center'},
		    {header : '总加组号', sortable: true, dataIndex : 'totalNo', align : 'center'}, 
		    {header : '电能表资产号', sortable: true, dataIndex : 'meterAssetNo', align : 'center',
		   		 renderer : function(value) {
		   		 	if(null!=value){
						var html = '<div align = "left" ext:qtitle="电能表资产号" ext:qtip="' + value + '">'
								+ value + '</div>';
						return html;
		   		 	}
					else
						return '';
					}},
		    {header : '执行日期', sortable: true, dataIndex : 'executeDate', align : 'center'},
		    {header : '执行状态', sortable: true, dataIndex : 'executeStatus', align : 'center'} 
		]);
	
	    var queryResultStore = new Ext.data.Store({
			   	url : './baseapp/prePaidStat!prePaidExecStatQuery.action',
			    reader : new Ext.data.JsonReader({
						root : 'prePaidExecStatusQueryList',
						totalProperty : 'totalCount'
					}, [
				    {name: 'orgName'},
				    {name: 'appNo'},
				    {name: 'consNo'},
				    {name: 'consName'},
				    {name: 'terminalAddr'},
				    {name: 'totalNo'},
				    {name: 'meterAssetNo'},
				    {name: 'executeDate'},
				    {name: 'executeStatus'}
			   ])
		});
		
		queryResultStore.baseParams={
			            orgNo:orgNo,
			            orgType:orgType,
						startDate:startDate,
						endDate:endDate,
						execStatus:execStatus
		           }; 
        queryResultStore.load({
     		params : {
				start : 0,
				limit : DEFAULT_PAGE_SIZE
			}
        });
        
		var queryResultGrid = new Ext.grid.GridPanel({
			   region:'center',
			   store : queryResultStore,
		       cm : queryResultCm,
		       stripeRows : true,	
		       autoScroll : true,
		       bbar : new Ext.ux.MyToolbar({
					store : queryResultStore,
					enableExpAll : true, // excel导出全部数据
				    expAllText : "全部",
					enableExpPage: true, // excel仅导出当前页
					expPageText : "当前页"
			   })
		});
		
		var prePaidExecStatQueryWindow =new Ext.Window({
			layout:'fit',
			title:title,
	        modal:true,
	     	maximizable:true, 
			width:800,
			height:550,
			minWidth:600,
			minHeight:412,
	     	items:[queryResultGrid]
		});
		
	    prePaidExecStatQueryWindow.show();
}		
		