/**
 * @author陈国章
 * @description 自定义报表
 * @version 1.00
 */
Ext.onReady(function() {
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
	var reportStore;
	var loadReportName=function(){		
			reportStore.baseParams = {reportType :reportTypeCombobox.getRawValue()};
			reportStore.load();			
	};
	var reportComboStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : './qrystat/reportTypeQueryAction!queryReportTypeList.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'reportList',
					fields : ['reportType', 'reportType']
				})
	});	
	// 报表类型下拉框
	var reportTypeCombobox = new Ext.form.ComboBox({
				id : 'report_type',
				triggerAction : 'all',
				bodyStyle : 'padding: 10px 10px 10px 10px',
				fieldLabel : '选择报表类型',
				store : reportComboStore,
				editable : false,
				mode : 'remote',
				labelWidth:75,
				width:100,
				valueField : 'reportType',
				displayField : 'reportType',
				emptyText : '--请选择--',
				selectOnFocus : true,
				listeners : {
					'select' : loadReportName
				},
				selectOnFocus : true
			});
			
//	reportTypeCombobox.on('select',loadReportName);
	var reportParamsPanel = new Ext.Panel({
		border : true,
		region : 'north',
		bodyStyle : 'padding:10px 0px',
		// autoHeight:true,
		layout : 'column',
		height : 40,
		items : [{
					border : false,
					columnWidth : .40,
					labelAlign : 'right',
					// labelWidth : 50,
					layout : 'form',
					items : [reportTypeCombobox]
				}]
	});
	
					
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
	 reportStore = new Ext.data.JsonStore({
//				proxy:new Ext.data.MemoryProxy(),
				url : './qrystat/reportTypeQueryAction!queryReportByType.action',
				fields:[ 'reportId','reportType','reportName','reportLen',
						'reportContent', 'reportParam','isValid'],
				root:'reportList',
				totalProperty : "reportCount"
			});
	// 报表类型和名称等信息的Grid
	reportTypeGrid = new Ext.grid.GridPanel({
				mode : 'remote',
				id : 'selfDefinitionReportGrid',
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
								html : "<font font-weight:bold;>自定义报表</font>"
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
				items : [reportParamsPanel,bottomGridPanel]

			});
	
/**------------------------------------综合操作----------------------------------- */

	// 上一步操作
	var previousProcess = function(btn) {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			} 
		else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
			 reportManCard.remove(reportParamsPanelSecond);
			 reportManCard.layout.setActiveItem(0);
			 previousButton.hide();
			}
	};
	// 下一步按钮处理方法
	nextProcess = function() {
		if (reportManCard.layout.activeItem == reportTypePanel) {
			var reportType=reportTypeCombobox.getRawValue();
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
				id:'selfDefinitionReport',
				buttonAlign : 'center',
				items : [reportTypePanel, reportParamsPanelSecond],
				buttons : [previousButton, nextButton]
			});
	previousButton.hide();
	renderModel(reportManCard, '自定义报表');
});