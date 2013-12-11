/**
 * @author陈国章
 * @description 报表查询
 * @version 1.00
 */
Ext.onReady(function() {
	var treeListener;
	var reportParamsPanelSecond = new Ext.Panel({});
	var userTerminal;//=  new Ext.grid.CheckboxSelectionModel({});
	var reportParamStore={};
	var reportParamsPanel={};
	var reportPanel={};
	var nextProcess;
	var reportTypeGrid={};
	var reportManCard={};
	var reportId;
	var getParamsUrl=function(){};
	var dataSelect=true;
	var timeSelect=true;
	
	var ParamsUrl='';
					
	/** -------------------------第一个页面---------------------------- */
	// 报表的列模型
//	var reportComboStore = new Ext.data.Store({
//
//		proxy : new Ext.data.HttpProxy({
//					url : './qrystat/reportTypeQueryAction!queryReportTypeList.action'
//				}),
//		reader : new Ext.data.JsonReader({
//					root : 'reportList',
//					fields : ['reportType', 'reportType']
//				})
//	});	
//	// 报表类型下拉框
//	var reportTypeCombobox = new Ext.form.ComboBox({
//				id : 'report_type',
//				triggerAction : 'all',
//				bodyStyle : 'padding: 10px 10px 10px 10px',
//				fieldLabel : '选择报表类型',
//				store : reportComboStore,
//				editable : false,
//				mode : 'remote',
//				valueField : 'reportType',
//				displayField : 'reportType',
//				emptyText : '--请选择--',
//				selectOnFocus : true,
//				listeners : {
//					select : loadReportName
//				},
//				selectOnFocus : true
//			});
	// reportTypeCombobox.on('select',loadReportName);
//	var topParamsTop = new Ext.FormPanel({
//				bodyStyle : 'padding:10px 0px',
//				region : 'north',
//				border : false,
//				plain : true,
//				height : 50,
//				layout : 'table',
//				layoutConfig : {
//					columns : 3
//				},
//				items : [{
//							layout : 'form',
//							border : false,
//							labelAlign : 'right',
//							items : [reportTypeCombobox]
//						}]
//			});

	var reportTypeCm = new Ext.grid.ColumnModel([ {
				header : '报表ID',
				dateIndex : 'reportId',
				align : 'left',
				hidden:true
				
			 },{
				header : '报表类型',
				dateIndex : 'reportType',
				align : 'left',
				hidden : true
				
			}, {
				header : '报表名称',
				dateIndex : 'reportName',
				align : 'left'
				
			}, {
				header : '报表长度',
				dateIndex : 'reportLen',
				align : 'left',
				hidden : true
			}, {
				header : '报表内容',
				dateIndex : 'reportContent',
				align : 'left',
				hidden : true
				
			}, {
				header : '报表描述',
				dateIndex : 'reportParam',
				align : 'left'
				
			}, {
				header : '是否有效',
				dateIndex : 'isValid',
				align : 'left',
				hidden:true
				
			}]);
	/** -----------------------存放报表信息的表格Store，第一个页面--------------- */
	var reportStore = new Ext.data.JsonStore({
//				proxy:new Ext.data.MemoryProxy(),
				url : './qrystat/reportTypeQueryAction!queryReportByType.action',
				fields:[ 'reportId','reportType','reportName','reportLen',
						'reportContent', 'reportParam','isValid'],
				root:'reportList',
				totalProperty : "reportCount"
			});
		reportStore.baseParams = {reportType:"电表数据报表"};
		reportStore.load();
			
//	Ext.Ajax.request({
//					url : './qrystat/reportTypeQueryAction!queryReportByType.action',
//					params : {
//						reportType :"电表数据报表"
//					},
//					success : function(response) {
//						var result = Ext.decode(response.responseText);
//						//alert(result.reportList);
//						reportStore.loadData(result);
//						
//					}
//				});
//	
	


	// 报表类型和名称等信息的Grid
	reportTypeGrid = new Ext.grid.GridPanel({
				mode : 'remote',
				id : 'reportTypeGrid',
				// ds : reportStore,
				region : 'south',
				cm : reportTypeCm,
				store : reportStore,
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				loadMask : false,// true,加载数据时--"读取中"
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				viewConfig : {
					forceFit :true
				},
				tbar : [{
								xtype : 'label',
								html : "<font font-weight:bold;>电表数据报表</font>"
							}]
			});
			
	// 利用Panel来存储GRID
	var bottomGridPanel = new Ext.Panel({
				layout : 'fit',
				region : 'center',
				autoScroll : true,
				border : false,
				plain : true,
				items : [reportTypeGrid]
			});
	// 第一个页面的综合Panel
	var reportTypePanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [bottomGridPanel]

			});
	/**
	 * ------------------------------
	 * 左边树监听------------------------------------------
	 */
	/** ********** */
//	new LeftTreeListener({
//		modelName : '电表数据报表',
//		processClick : function(p, node, e) {
//			var obj = node.attributes.attributes;
//			var type = node.attributes.type;
//			reportParamStore.load({
//						params : {
//							"node" : type == "usr"
//							? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
//							: node.id
//						}
//					});
//			reportStore.load({
//						params : {
//							start : 0,
//							limit : 50,
//							node : node.id
//						}
//					});
//			Ext.Ajax.request({
//				url : "baseapp/dataFetch!dealTree.action",
//				params : {
//					start : 0,
//					limit : 400,
//					"node" : type == "usr"
//							? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
//							: node.id
//				},
//				success : function(response) {
//					var o = Ext.decode(response.responseText);
//					storeTerminalGrid.addDatas(o.resultMap);
//
//				}
//			});

//    },
//		processUserGridSelect : function(sm, row, r) {
//			var tmnl = r.get("tmnlAssetNo");
//			reportParamStore.load({
//						params : {
//							node : "tmnl_" + tmnl + "_01"
//						}
//					});}
//			Ext.Ajax.request({
//						url : "baseapp/dataFetch!dealTree.action",
//						params : {
//							start : 0,
//							limit : 50,
//							"node" : "tmnl_" + tmnl + "_01"
//						},
//						success : function(response) {
//							var o = Ext.decode(response.responseText);
//							reportParamStore.addDatas(o.resultMap);
//						}
//					});
//		}
//	});

	/** ------------------------------第二个页面-------------------------------------- */

	// 电表数据报表参数
//	var reportParamsPanel = new Ext.Panel({
//				border : true,
//				region : 'north',
//				bodyStyle : 'padding:10px 0px',
//				// autoHeight:true,
//				layout : 'column',
//				height : 40,
//				items : [{
//							border : false,
//							columnWidth : .30,
//							labelAlign : 'right',
//							// labelWidth : 50,
//							layout : 'form',
//							items : [{
//										id : 'time_tmnl',
//										xtype : 'datefield',
//										// labelSeparator : ' ',
//										// width : 85,
//										value : new Date(),
//										fieldLabel : '请选择时间',
//										format : "Y-m-d"
//									}]
//						}]
//			});

	/** ******** */
	/* 用户及其终端列表 */
	/** ******** */
//	var userTerminal = new Ext.grid.CheckboxSelectionModel({});
//	var userTerminaNumber = new Ext.grid.RowNumberer({
//		renderer : function(v, p, record, rowIndex) {
//			if (this.rowspan) {
//				p.cellAttr = 'rowspan="' + this.rowspan + '"';
//			}
//
//			reportParamStore.lastOptions.params = reportParamStore.lastOptions.params
//					|| {
//						start : 0
//					};
//			return reportParamStore.lastOptions.params.start||0 + rowIndex + 1;
//		}
//
//	});

	// 上一步操作
	var previousProcess = function(btn) {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			} else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
			 treeListener.removeListener();
			 reportManCard.layout.setActiveItem(0);
			 reportManCard.remove(reportParamsPanelSecond);
			 previousButton.hide();		
		}
	};
	// 下一步按钮处理方法
	nextProcess = function() {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			var reportType='电表数据报表';
			if((Ext.isEmpty(reportTypeGrid.getSelectionModel().getSelected()))){
				Ext.MessageBox.alert('提示','请选择报表');
				return;
			}
			var reportName=reportTypeGrid.getSelectionModel().getSelected().get('reportName');
			reportId=reportTypeGrid.getSelectionModel().getSelected().get('reportId');
    		reportManCard.remove(reportParamsPanelSecond);
			Ext.Ajax.request({
					url : './qrystat/reportTypeQueryAction!queryJSFile.action',
					params : {
						reportType : reportType,
						reportName:  reportName
						},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var scriptConent = result.scriptConent;
						eval(scriptConent);
//						getParamsUrl();
						

						reportManCard.add(reportParamsPanelSecond);
						
						reportManCard.doLayout();
						reportManCard.layout.setActiveItem(1);
						previousButton.show();
					}
				});
				
				
		}
		 else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
		 	getParamsUrl();
		 	ParamsUrl=ParamsUrl+"&reportId="+reportId;
		 	if(dataSelect&&timeSelect)
		 	{
		 		window.open("/sea/Report.servlet?"+ParamsUrl);
		 	}
 }
//	   var reportClass="com.nari.qrystat.reportMan.ReportFactory"
//	   var reportUrl="/sea/ReportServer.servlet?reportlet="+reportClass+"&reportId=2";//+consParamsList;
//	   var panel= new Ext.Panel({
//							title : '电表数据报表',
//							id : '电表数据报表',
//							iconCls : 'mainpage',
//							layout : 'fit',
//							autoLoad : {
//								url : reportUrl,
//								nocache : true,
//								text : 'Loading...',
//								scripts : false
//							}
//						})
//		window.open("http://localhost:8080/sea/Report.servlet?reportId=312")
	    
//		 openTab("采集覆盖情况统计表","http://localhost:8080/sea/ReportServer.servlet?reportlet=com.nari.qrystat.reportMan.ReportFactory&reportId=2");
//		 reportManCard.layout.setActiveItem(2);	  		    
//			  		    alert(consParamsList);
//        var panel=new Ext.Panel({
//        	url:'http://localhost:8080/sea/ReportServer.servlet?reportlet=com.nari.reportMan.ReportFactory',
//        	title:'电表数据报表'
//        });
//        alert(panel.title);
//        reportManCard.add(panel);
//        reportManCard.layout.setActiveItem(2)
			  		    
                       

	};

	// 上一步按钮
	var previousButton = new Ext.Button({
				text : '上一步',
				handler : previousProcess

			});
	// 下一步按钮
	var nextButton = new Ext.Button({
				text : '下一步',
				handler:nextProcess
			});

	// 综合界面
	reportManCard = new Ext.Panel({
				border : false,
				region : 'center',
				activeItem : 0,
				layout : 'card',
				buttonAlign : 'center',
				id:'meterDataReport',
				items : [reportTypePanel, reportParamsPanelSecond],
				buttons : [previousButton, nextButton]
			});
	previousButton.hide();
//	var win = new Ext.Window({
// 
//  title: '明细',
//  width: 600,
//  height: 400,
//  html: '<iframe />',
//  buttons: [{
//    text: '打印',
//    handler: function(){
//      var c = win.body.first().dom.innerHTML;
//      var printer = win.body.last().dom.contentWindow;
//      printer.document.body.innerHTML = c;
//      printer.focus()
//      printer.print();
//    }
//  }]
//});

	renderModel(reportManCard, '电表数据报表');
});