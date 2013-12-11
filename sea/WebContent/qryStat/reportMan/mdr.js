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
				header : '报表描述',
				dateIndex : 'reportParam',
				align : 'left'
				
			},{
				header : '是否是自定义报表',
				dateIndex : 'defBoolean',
				align : 'left',
				hidden:true
			}]);
	/** -----------------------存放报表信息的表格Store，第一个页面--------------- */
	var reportStore = new Ext.data.JsonStore({
				url : './qrystat/reportTypeQueryAction!queryReportInfoByType.action',
				fields:[ 'reportId','reportType','reportName','reportParam','defBoolean'],
				root:'reportList',
				totalProperty : "reportCount"
			});
		reportStore.baseParams = {reportType:"电表数据报表"};
		reportStore.load();
	// 报表类型和名称等信息的Grid
	reportTypeGrid = new Ext.grid.GridPanel({
				mode : 'remote',
				id : 'reportTypeGrid',
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
	/** ------------------------------第二个页面-------------------------------------- */

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
			if((Ext.isEmpty(reportTypeGrid.getSelectionModel().getSelected()))){
				Ext.MessageBox.alert('提示','请选择报表');
				return;
			}
			reportId=reportTypeGrid.getSelectionModel().getSelected().get('reportId');
    		reportManCard.remove(reportParamsPanelSecond);
			Ext.Ajax.request({
					url : './qrystat/reportTypeQueryAction!queryJSFileById.action',
					params : {
						reportId : reportId
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
				buttonAlign : 'center',
				items : [reportTypePanel, reportParamsPanelSecond],
				buttons : [previousButton, nextButton]
			});
	previousButton.hide();
	renderModel(reportManCard, '电表数据报表');
});